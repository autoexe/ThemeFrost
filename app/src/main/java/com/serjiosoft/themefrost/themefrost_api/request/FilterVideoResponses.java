package com.serjiosoft.themefrost.themefrost_api.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.serjiosoft.themefrost.themefrost_api.models_api.Album;
import com.serjiosoft.themefrost.themefrost_api.models_api.Group;
import com.serjiosoft.themefrost.themefrost_api.models_api.User;
import com.serjiosoft.themefrost.themefrost_api.models_api.Video;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKScopes;

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


    public static <T> T parseWithGson(String json, Type type) {
        return new Gson().fromJson(json, type);
    }


    public static ArrayList<Video> getVideos(VKResponse vKResponse)throws JSONException {
        JSONObject response = getResponseFromMainJson(vKResponse.json);
        ArrayList<Video> videos = parseWithGson(response.getJSONArray(VKResponseConstants.KEY_ITEMS).toString(), typeTokenVideoArray);
        if (videos.size() != 0){
            prepareVideo(videos, (ArrayList) parseWithGson(response.getJSONArray(VKScopes.GROUPS).toString(), typeTokenGroupArray)
                    , (ArrayList) parseWithGson(response.getJSONArray(VKResponseConstants.KEY_PROFILES).toString(), typeTokenUserArray));
        }
        return videos;
    }



    private static JSONObject getResponseFromMainJson(JSONObject json)throws JSONException {
        return json.getJSONObject(VKResponseConstants.KEY_RESPONSE);
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

}
