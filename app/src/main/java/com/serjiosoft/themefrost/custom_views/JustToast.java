package com.serjiosoft.themefrost.custom_views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.serjiosoft.themefrost.R;

/**
 * Created by autoexec on 01.03.2017.
 */

public class JustToast {

    public static void makeAndShow(Context context, CharSequence text, int duration) {
        getInflatedToastWithTitle(context, text, duration).show();
    }

    private static Toast getInflatedToastWithTitle(Context context, CharSequence text, int duration) {
        Toast toast = new Toast(context);
        View toastView = LayoutInflater.from(context).inflate(R.layout.theme_toast, null, false);
        ((TextView) toastView.findViewById(R.id.tvToastMessage_TT)).setText(text);
        toast.setView(toastView);
        toast.setDuration(duration);
        return toast;
    }

}
