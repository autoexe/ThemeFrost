package com.serjiosoft.themefrost.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by autoexec on 24.02.2017.
 */

public class SharedPrefsController {

    public static final String KEY_ADS = "key_ads";
    public static final String KEY_FILTER_DURATION = "key_filter_duration";
    public static final String KEY_IS_HD = "key_is_hd";
    public static final String KEY_IS_REMOVED_ADS = "key_ads_removed";
    public static final String KEY_ON_FILTERS = "key_filters";
    public static final String KEY_ON_MX_PLAYER = "key_mx_player";
    public static final String KEY_SAFE_SEARCH = "key_safe_search";
    public static final String KEY_SORT_OPTION = "key_sort_option";
    public static final String KEY_THEME = "key_theme";
    public static final String KEY_USER = "key_user";
    private static final String MODE_SLOT = "VK_VIDEO_MANAGER_MODE_PRIVATE";


    private static SharedPreferences getPrefs(Context applicatioContext) {
        return applicatioContext.getSharedPreferences(MODE_SLOT, 0);
    }

    public static String getFromPref(Context context, String key) {
        return getPrefs(context.getApplicationContext()).getString(key, null);
    }

    public static void saveToPref(Context context, String key, String value) {
        SharedPreferences.Editor spEditor = getPrefs(context.getApplicationContext()).edit();
        spEditor.putString(key, value);
        spEditor.commit();
    }


    public static void saveToPref(Context context, String key, boolean value) {
        SharedPreferences.Editor spEditor = getPrefs(context.getApplicationContext()).edit();
        spEditor.putBoolean(key, value);
        spEditor.commit();
    }


    public static void saveToPref(Context context, String key, int value) {
        SharedPreferences.Editor spEditor = getPrefs(context.getApplicationContext()).edit();
        spEditor.putInt(key, value);
        spEditor.commit();
    }


    public static int getIntFromPref(Context context, String key) {
        return getPrefs(context.getApplicationContext()).getInt(key, 0);
    }


    public static boolean getBoolFromPref(Context context, String key) {
        return getPrefs(context.getApplicationContext()).getBoolean(key, false);
    }

}
