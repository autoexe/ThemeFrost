package com.serjiosoft.themefrost.themefrost_api.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.serjiosoft.themefrost.themefrost_api.models_api.Album;
import com.serjiosoft.themefrost.themefrost_api.models_api.Catalog;
import com.serjiosoft.themefrost.themefrost_api.models_api.CatalogKid;
import com.serjiosoft.themefrost.themefrost_api.models_api.Group;
import com.serjiosoft.themefrost.themefrost_api.models_api.User;
import com.serjiosoft.themefrost.themefrost_api.models_api.Video;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKScopes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by autoexec on 28.02.2017.
 */

public final class FilterVideoResponses {

    public static final Type typeTokenAlbumArray = new TypeToken<ArrayList<Album>>() {
    }.getType();
    public static final Type typeTokenGroupArray = new TypeToken<ArrayList<Group>>() {
    }.getType();
    public static final Type typeTokenUserArray = new TypeToken<ArrayList<User>>() {
    }.getType();
    public static final Type typeTokenVideoArray = new TypeToken<ArrayList<Video>>() {
    }.getType();


    public static ArrayList<Video> getVideos(VKResponse vkResponse) throws JSONException {
        JSONObject response = getResponseFromMainJson(vkResponse.json);
        ArrayList<Video> videos = (ArrayList) parseWithGson(response.getJSONArray(VKResponseConstants.KEY_ITEMS).toString(), typeTokenVideoArray);
        if (videos.size() != 0) {
            prepareVideo(videos, (ArrayList) parseWithGson(response.getJSONArray(VKScopes.GROUPS).toString(), typeTokenGroupArray), (ArrayList) parseWithGson(response.getJSONArray(VKResponseConstants.KEY_PROFILES).toString(), typeTokenUserArray));
        }
        return videos;
    }

    public static ArrayList<Catalog> getCatalogs(VKResponse vkResponse) throws JSONException {
        JSONObject response = getResponseFromMainJson(vkResponse.json);
        JSONArray items = response.getJSONArray(VKResponseConstants.KEY_ITEMS);
        ArrayList<Group> groups = (ArrayList) parseWithGson(response.getJSONArray(VKScopes.GROUPS).toString(), typeTokenGroupArray);
        ArrayList<User> profiles = (ArrayList) parseWithGson(response.getJSONArray(VKResponseConstants.KEY_PROFILES).toString(), typeTokenUserArray);
        ArrayList<Catalog> catalogs = new ArrayList();
        for (int i = 0; i < items.length(); i++) {
            Catalog preparedCatalog = getParsedCatalog(items.getJSONObject(i));
            if (preparedCatalog.items.size() > 0) {
                catalogs.add(preparedCatalog);
            }
        }
        prepareCatalogItems(catalogs, groups, profiles);
        return catalogs;
    }

    private static Catalog getParsedCatalog(JSONObject json) throws JSONException {
        String string;
        String str = null;
        Catalog catalog = new Catalog();
        catalog.id = json.getString(VKResponseConstants.KEY_ID);
        catalog.name = json.getString(VKResponseConstants.KEY_NAME);
        if (json.has(VKResponseConstants.KEY_TYPE)) {
            string = json.getString(VKResponseConstants.KEY_TYPE);
        } else {
            string = null;
        }
        catalog.type = string;
        if (json.has(VKResponseConstants.KEY_NEXT)) {
            str = json.getString(VKResponseConstants.KEY_NEXT);
        }
        catalog.next = str;
        catalog.items = getItemsCatalog(json.getJSONArray(VKResponseConstants.KEY_ITEMS));
        return catalog;
    }

    private static ArrayList<CatalogKid> getItemsCatalog(JSONArray itemsArray) throws JSONException {
        ArrayList<CatalogKid> mCatalogItems = new ArrayList();
        for (int j = 0; j < itemsArray.length(); j++) {
            JSONObject jObject = itemsArray.getJSONObject(j);
            if (jObject.getString(VKResponseConstants.KEY_TYPE).equals(VKAttachments.TYPE_VIDEO)) {
                mCatalogItems.add(parseWithGson(jObject.toString(), Video.class));
            } else {
                mCatalogItems.add(parseWithGson(jObject.toString(), Album.class));
            }
        }
        return mCatalogItems;
    }

    public static ArrayList<Video> getVideosFromWallResponse(VKResponse vkResponse) throws JSONException {
        JSONObject response = getResponseFromMainJson(vkResponse.json);
        JSONArray arrayPosts = response.getJSONArray(VKResponseConstants.KEY_ITEMS);
        ArrayList<Video> videos = new ArrayList();
        for (int i = 0; i < arrayPosts.length(); i++) {
            JSONObject obj = arrayPosts.getJSONObject(i);
            videos.addAll(getVideoIfExistAttachments(obj));
            videos.addAll(getVideoIfExistCopyHistory(obj));
        }
        if (videos.size() != 0) {
            prepareVideo(videos, (ArrayList) parseWithGson(response.getJSONArray(VKScopes.GROUPS).toString(), typeTokenGroupArray), (ArrayList) parseWithGson(response.getJSONArray(VKResponseConstants.KEY_PROFILES).toString(), typeTokenUserArray));
        }
        return videos;
    }

    private static ArrayList<Video> getVideoIfExistCopyHistory(JSONObject jo) throws JSONException {
        ArrayList<Video> videoHistories = new ArrayList();
        if (jo.has(VKResponseConstants.KEY_COPY_HISTORY)) {
            JSONArray copyHistories = jo.getJSONArray(VKResponseConstants.KEY_COPY_HISTORY);
            for (int k = 0; k < copyHistories.length(); k++) {
                videoHistories.addAll(getVideoIfExistAttachments(copyHistories.getJSONObject(k)));
            }
        }
        return videoHistories;
    }

    private static ArrayList<Video> getVideoIfExistAttachments(JSONObject jo) throws JSONException {
        ArrayList<Video> videosAttachments = new ArrayList();
        if (jo.has(VKApiConst.ATTACHMENTS)) {
            JSONArray attachments = jo.getJSONArray(VKApiConst.ATTACHMENTS);
            for (int j = 0; j < attachments.length(); j++) {
                JSONObject attachment = attachments.getJSONObject(j);
                if (attachment.getString(VKResponseConstants.KEY_TYPE).equals(VKAttachments.TYPE_VIDEO)) {
                    Video video = getVideoFromJson(attachment.getJSONObject(VKAttachments.TYPE_VIDEO));
                    video.chOwnerId = jo.getInt(VKApiConst.OWNER_ID);
                    video.adding_date = (long) jo.getInt(VKResponseConstants.KEY_DATE);
                    videosAttachments.add(video);
                }
            }
        }
        return videosAttachments;
    }

    public static ArrayList<Catalog> getVideosFromNewsResponse(VKResponse vkResponse) throws JSONException {
        JSONObject response = getResponseFromMainJson(vkResponse.json);
        JSONArray arrayPosts = response.getJSONArray(VKResponseConstants.KEY_ITEMS);
        ArrayList<Catalog> catalogNews = new ArrayList();
        for (int i = 0; i < arrayPosts.length(); i++) {
            JSONObject itemObject = arrayPosts.getJSONObject(i);
            Catalog catalog = new Catalog();
            catalog.id = String.valueOf(itemObject.getInt(VKResponseConstants.SOURCE_ID));
            catalog.date = (long) itemObject.getInt(VKResponseConstants.KEY_DATE);
            catalog.items = (ArrayList) parseWithGson(itemObject.getJSONObject(VKAttachments.TYPE_VIDEO).getJSONArray(VKResponseConstants.KEY_ITEMS).toString(), typeTokenVideoArray);
            if (catalog.items.size() > 0) {
                catalogNews.add(catalog);
            }
        }
        if (catalogNews.size() != 0) {
            prepareCatalogItems(catalogNews, (ArrayList) parseWithGson(response.getJSONArray(VKScopes.GROUPS).toString(), typeTokenGroupArray), (ArrayList) parseWithGson(response.getJSONArray(VKResponseConstants.KEY_PROFILES).toString(), typeTokenUserArray));
        }
        return catalogNews;
    }

    private static void prepareCatalogItems(ArrayList<Catalog> catalogs, ArrayList<Group> groups, ArrayList<User> profiles) {
        Iterator it = catalogs.iterator();
        while (it.hasNext()) {
            Catalog catalog = (Catalog) it.next();
            try {
                int idCatalog = Integer.parseInt(catalog.id);
                if (idCatalog < 0) {
                    catalog.group = getOwnerFromGroups(groups, -idCatalog);
                } else {
                    catalog.profile = getOwnerFromProfiles(profiles, idCatalog);
                }
            } catch (NumberFormatException e) {
            }
            Iterator i$ = catalog.items.iterator();
            while (i$.hasNext()) {
                CatalogKid catalogKid = (CatalogKid) i$.next();
                if (catalogKid instanceof Video) {
                    Video video = (Video) catalogKid;
                    if (video.getOwnerId() < 0) {
                        video.group = getOwnerFromGroups(groups, -video.getOwnerId());
                    } else {
                        video.profile = getOwnerFromProfiles(profiles, video.getOwnerId());
                    }
                } else {
                    Album album = (Album) catalogKid;
                    if (album.owner_id < 0) {
                        album.group = getOwnerFromGroups(groups, -album.owner_id);
                    } else {
                        album.profile = getOwnerFromProfiles(profiles, album.owner_id);
                    }
                }
            }
        }
    }

    private static void prepareVideo(ArrayList<Video> videos, ArrayList<Group> groups, ArrayList<User> profiles) {
        Iterator i$ = videos.iterator();
        while (i$.hasNext()) {
            Video video = (Video) i$.next();
            if (video.getOwnerId() < 0) {
                video.group = getOwnerFromGroups(groups, -video.getOwnerId());
            } else {
                video.profile = getOwnerFromProfiles(profiles, video.getOwnerId());
            }
        }
    }

    private static Group getOwnerFromGroups(ArrayList<Group> groups, int ownerId) {
        Iterator i$ = groups.iterator();
        while (i$.hasNext()) {
            Group group = (Group) i$.next();
            if (group.id == ownerId) {
                return group;
            }
        }
        return null;
    }

    private static User getOwnerFromProfiles(ArrayList<User> profiles, int ownerId) {
        Iterator i$ = profiles.iterator();
        while (i$.hasNext()) {
            User user = (User) i$.next();
            if (user.id == ownerId) {
                return user;
            }
        }
        return null;
    }

    public static <T> T parseWithGson(String json, Type type) {
        return new Gson().fromJson(json, type);
    }

    public static <T> T parseWithGson(String json, Class<T> classOfT) {
        return (T) new Gson().fromJson(json, (Class) classOfT);
    }

    private static JSONObject getResponseFromMainJson(JSONObject json) throws JSONException {
        return json.getJSONObject(VKResponseConstants.KEY_RESPONSE);
    }

    private static Video getVideoFromJson(JSONObject videoObj) {
        return (Video) new Gson().fromJson(videoObj.toString(), Video.class);
    }

}
