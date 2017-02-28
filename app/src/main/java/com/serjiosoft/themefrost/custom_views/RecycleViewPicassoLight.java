package com.serjiosoft.themefrost.custom_views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.serjiosoft.themefrost.R;
import com.squareup.picasso.Picasso;

/**
 * Created by autoexec on 24.02.2017.
 */

public class RecycleViewPicassoLight extends RecyclerView {

    public static final String PICASSO_TAG = "jvv_picasso_resume_pause_tag";
    private Picasso picasso;


    public RecycleViewPicassoLight(Context context) {
        super(context);
        initPicasso();
    }


    public RecycleViewPicassoLight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPicasso();
    }


    public RecycleViewPicassoLight(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPicasso();
    }

    private void initPicasso() {
        picasso = Picasso.with(getContext());
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == 0 || state == 1) {
            picasso.resumeTag(PICASSO_TAG);
        } else {
            picasso.pauseTag(PICASSO_TAG);
        }
    }
}
