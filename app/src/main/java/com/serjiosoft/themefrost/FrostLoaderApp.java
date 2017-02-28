package com.serjiosoft.themefrost;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.vk.sdk.VKSdk;

/**
 * Created by autoexec on 24.02.2017.
 */

public class FrostLoaderApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.customInitialize(getApplicationContext(), 5210325, "5.21");
    }

}
