package com.serjiosoft.themefrost.custom_views;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by autoexec on 24.02.2017.
 */

public class MyCardView extends FrameLayout {


    public MyCardView(@NonNull Context context) {
        super(context);
    }

    public MyCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCardView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
