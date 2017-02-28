package com.serjiosoft.themefrost.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.serjiosoft.themefrost.R;

/**
 * Created by autoexec on 22.02.2017.
 */

public class TintImageView extends android.support.v7.widget.AppCompatImageView {


    public TintImageView(Context context) {
        super(context);
        beforeConfigs(null, 0);
    }

    public TintImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        beforeConfigs(attrs, 0);
    }

    public TintImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        beforeConfigs(attrs, defStyleAttr);
    }

    private void beforeConfigs(AttributeSet attrs, int defStyle){
        int colorTint;
        if (attrs == null) {
            colorTint = ViewCompat.MEASURED_STATE_MASK;
        } else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TintImageView, defStyle, 0);
            colorTint = a.getColor(0, ViewCompat.MEASURED_STATE_MASK);
            a.recycle();
        }
        setColorFilter(colorTint);
    }
}
