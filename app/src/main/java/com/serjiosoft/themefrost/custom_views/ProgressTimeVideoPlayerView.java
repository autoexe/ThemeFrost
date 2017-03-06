package com.serjiosoft.themefrost.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;


import com.google.android.gms.common.ConnectionResult;
import com.serjiosoft.themefrost.R;

/**
 * Created by autoexec on 01.03.2017.
 */

public class ProgressTimeVideoPlayerView extends View {

    private Paint mDrawPaint;
    private IMoveControlInterface mIMoveControlInterface;
    private ProgressBar mProgressView;
    private GestureDetector mSingleTapConfirmDetector;
    private int mStartProgressWhenTouch;

    public interface IMoveControlInterface {
        void onClickPP();

        void pauseListenProgressVideo(boolean z);

        void seekToProgressValue();
    }

    private class SimplePlayerGesture extends GestureDetector.SimpleOnGestureListener {
        private SimplePlayerGesture() {
        }

        public boolean onDown(MotionEvent e) {
            ProgressTimeVideoPlayerView.this.mStartProgressWhenTouch = ProgressTimeVideoPlayerView.this.mProgressView.getProgress();
            return true;
        }

        public boolean onSingleTapUp(MotionEvent e) {
            ProgressTimeVideoPlayerView.this.mIMoveControlInterface.onClickPP();
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            ProgressTimeVideoPlayerView.this.setAlpha(1.0f);
            ProgressTimeVideoPlayerView.this.addToProgress((e2.getX() - e1.getX()) / ((float) ProgressTimeVideoPlayerView.this.getWidth()));
            return true;
        }
    }


    public ProgressTimeVideoPlayerView(Context context) {
        super(context);
    }

    public ProgressTimeVideoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public ProgressTimeVideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void initPaint() {
        this.mSingleTapConfirmDetector = new GestureDetector(getContext(), new SimplePlayerGesture());
        this.mDrawPaint = new Paint(1);
        this.mDrawPaint.setColor(getResources().getColor(R.color.seek_bar_bg));
    }


    private RectF getDrawProgressRect() {
        return new RectF(0.0f, 0.0f, (((float) this.mProgressView.getProgress()) / ((float) this.mProgressView.getMax())) * ((float) getWidth()), (float) getHeight());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(getDrawProgressRect(), this.mDrawPaint);
    }

    private void startAlphaAnimation() {
        animate().alpha(0.0f).setDuration(500).setStartDelay(500).setInterpolator(new LinearInterpolator()).start();
    }

    public void setProgressView(ProgressBar progressView, IMoveControlInterface iMoveControlInterface) {
        mProgressView = progressView;
        mIMoveControlInterface = iMoveControlInterface;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case ConnectionResult.SUCCESS /*0*/:
                this.mIMoveControlInterface.pauseListenProgressVideo(false);
                break;
            case ConnectionResult.SERVICE_MISSING /*1*/:
                if (getAlpha() > 0.0f) {
                    startAlphaAnimation();
                }
                this.mIMoveControlInterface.pauseListenProgressVideo(true);
                this.mIMoveControlInterface.seekToProgressValue();
                break;
        }
        return this.mSingleTapConfirmDetector.onTouchEvent(event);
    }



    private void addToProgress(float valuePercent) {
        if (this.mProgressView != null && this.mProgressView.getMax() > 0) {
            this.mProgressView.setProgress(Math.min(Math.max(0, this.mStartProgressWhenTouch + ((int) (((float) this.mProgressView.getMax()) * valuePercent))), this.mProgressView.getMax()));
            invalidate();
        }
    }

}
