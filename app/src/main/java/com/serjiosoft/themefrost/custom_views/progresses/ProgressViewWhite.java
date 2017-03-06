package com.serjiosoft.themefrost.custom_views.progresses;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.serjiosoft.themefrost.R;

/**
 * Created by autoexec on 24.02.2017.
 */

public class ProgressViewWhite extends FrameLayout {

    private ImageView mProgressImageView;


    public ProgressViewWhite(@NonNull Context context) {
        super(context);
    }

    public ProgressViewWhite(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.progress_view_white, this);

        mProgressImageView = (ImageView) findViewById(R.id.tivProgressView_PV);

        startIndeterminateAnimation();

    }

    public ProgressViewWhite(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startIndeterminateAnimation() {
        this.mProgressImageView.clearAnimation();
        this.mProgressImageView.startAnimation(getInfinityAnimation());
    }


    private RotateAnimation getInfinityAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setStartOffset(0);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setRepeatMode(-1);
        rotateAnimation.setDuration(1800);
        return rotateAnimation;
    }

}
