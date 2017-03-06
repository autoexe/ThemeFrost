package com.serjiosoft.themefrost.fragments;

import com.serjiosoft.themefrost.BuildConfig;
import com.serjiosoft.themefrost.R;

/**
 * Created by autoexec on 01.03.2017.
 */

public final class VideoUIUtils {

    public static int getSeeTitleRes(int value){
        return R.string.prosmotrov;
    }

    public static String getDuration(int duration){
        String hours = duration / 3600 == 0 ? " " : Integer.toString(Math.min(59, duration / 3600));
        duration %= 3600;
        String minutes = Integer.toString(duration / 60);
        String seconds = Integer.toString(duration % 60);
        String resultSecond = " ";
        if (!hours.equals(" ")) {
            resultSecond = resultSecond + (hours.length() == 1 ? "0" : " ") + hours + ":";
        }
        return (resultSecond + (minutes.length() == 1 ? "0" : " ") + minutes + ":") + (seconds.length() == 1 ? "0" : " ") + seconds;

    }
}
