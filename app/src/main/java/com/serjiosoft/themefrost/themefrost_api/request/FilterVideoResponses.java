package com.serjiosoft.themefrost.themefrost_api.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.serjiosoft.themefrost.themefrost_api.models_api.Album;
import com.serjiosoft.themefrost.themefrost_api.models_api.Group;
import com.serjiosoft.themefrost.themefrost_api.models_api.User;
import com.serjiosoft.themefrost.themefrost_api.models_api.Video;

import java.lang.reflect.Type;
import java.util.ArrayList;

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


}
