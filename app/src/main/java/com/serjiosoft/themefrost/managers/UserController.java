package com.serjiosoft.themefrost.managers;

import android.content.Context;

import com.google.gson.Gson;
import com.serjiosoft.themefrost.preferences.SharedPrefsController;
import com.serjiosoft.themefrost.themefrost_api.models_api.User;

/**
 * Created by autoexec on 24.02.2017.
 */

public final class UserController {

    private static User user;

    public static void storeUser(User _user) {
        user = _user;
    }

    public static User getUser(Context context){
        if (user == null){
            user = (User) new Gson().fromJson(SharedPrefsController.getFromPref(context, SharedPrefsController.KEY_USER), User.class);
        }
        return user;
    }
}
