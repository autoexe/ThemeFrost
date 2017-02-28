package com.serjiosoft.themefrost.managers;

import android.util.Log;

/**
 * Created by autoexec on 24.02.2017.
 */

public final class JustLog {

    public static boolean isRelease = true;

    public static void d(String tag, String msg) {
        if (!isRelease) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (!isRelease) {
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (!isRelease) {
            Log.e(tag, msg);
        }
    }
}
