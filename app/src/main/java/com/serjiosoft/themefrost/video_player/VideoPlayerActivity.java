package com.serjiosoft.themefrost.video_player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.media.TransportMediator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.serjiosoft.themefrost.BaseActivity;
import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.builder_intent.ActivityIntentBuilder;
import com.serjiosoft.themefrost.builder_intent.PostActivityStarter;
import com.serjiosoft.themefrost.custom_views.JustToast;
import com.serjiosoft.themefrost.custom_views.ProgressTimeVideoPlayerView;
import com.serjiosoft.themefrost.custom_views.progresses.ProgressViewWhite;
import com.serjiosoft.themefrost.fragments.VideoUIUtils;
import com.serjiosoft.themefrost.managers.JustLog;
import com.serjiosoft.themefrost.themefrost_api.models_api.Video;

import java.io.Serializable;

/**
 * Created by autoexec on 01.03.2017.
 */

public class VideoPlayerActivity extends BaseActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, ProgressTimeVideoPlayerView.IMoveControlInterface, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener {


    private boolean isContinueListenTime = true;
    private boolean isPauseAfter = false;
    private boolean isPrepared = false;
    private boolean isPreparing = false;

    public static final String M_QUALITY_EXTRA = "mQuality";
    public static final String M_VIDEO_FOR_PLAYING_EXTRA = "mVideoForPlaying";

    protected int mQuality;
    protected Video mVideoForPlaying;

    protected LinearLayout mBottomContainer;
    private int mCurrentTime;
    private Handler mHanlerPlayingTracker = new Handler();
    protected TextView mLeftTime;
    protected CheckBox mPlayPause;
    protected ImageView mPlayPauseIntro;
    protected ProgressTimeVideoPlayerView mProgressTimeView;
    protected ProgressBar mProgressVideoView;
    protected ProgressViewWhite mProgressViewWhite;
    protected TextView mRightTime;
    protected TextView mSubTitle;
    protected TextView mTitle;
    protected LinearLayout mTopContainer;
    protected VideoView mVideoView;




    public static class IntentBuilder_ extends ActivityIntentBuilder<IntentBuilder_> {

        private Fragment fragmentSupport;
        private android.app.Fragment fragment;

        public IntentBuilder_(Context context) {
            super(context, VideoPlayerActivity.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), VideoPlayerActivity.class);
            this.fragment = fragment;
        }

        public IntentBuilder_(Fragment fragment) {
            super(fragment.getActivity(), VideoPlayerActivity.class);
            this.fragmentSupport = fragment;
        }


        @Override
        public PostActivityStarter startForResult(int requestCode) {
            if (fragmentSupport != null) {
                fragmentSupport.startActivityForResult(intent, requestCode);
            } else if (this.fragment != null) {
                if (Build.VERSION.SDK_INT >= 16) {
                    this.fragment.startActivityForResult(intent, requestCode, lastOptions);
                } else {
                    this.fragment.startActivityForResult(intent, requestCode);
                }
            } else if (this.context instanceof Activity) {
                ActivityCompat.startActivityForResult((Activity) context, intent, requestCode, lastOptions);
            } else if (Build.VERSION.SDK_INT >= 16) {
                this.context.startActivity(this.intent, this.lastOptions);
            } else {
                this.context.startActivity(this.intent);
            }

            return null;
        }

        public IntentBuilder_ mQuality(int mQuality) {
            return super.extra(VideoPlayerActivity.M_QUALITY_EXTRA, mQuality);
        }

        public IntentBuilder_ mVideoForPlaying(Video mVideoForPlaying) {
            return super.extra(VideoPlayerActivity.M_VIDEO_FOR_PLAYING_EXTRA, mVideoForPlaying);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        injectExtras();
        offDisplaySleep();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        mBottomContainer = (LinearLayout) findViewById(R.id.llVideoControllerContainer_AVP);
        mLeftTime = (TextView) findViewById(R.id.tvLeftTime_AVP);
        mTitle = (TextView) findViewById(R.id.tvTitleVideo_AVP);
        mPlayPauseIntro = (ImageView) findViewById(R.id.ivPlayPauseIntro_AVP);
        mVideoView = (VideoView) findViewById(R.id.videoPlayer_AVP);
        mTopContainer = (LinearLayout) findViewById(R.id.llTitleVideoContainer_AVP);
        mRightTime = (TextView) findViewById(R.id.tvRightTime_AVP);
        mSubTitle = (TextView) findViewById(R.id.tvOwnerTitleVideo_AVP);
        mProgressTimeView = (ProgressTimeVideoPlayerView) findViewById(R.id.ptvpvPlayer_AVP);
        mPlayPause = (CheckBox) findViewById(R.id.cbPlayPause_AVP);
        mProgressVideoView = (ProgressBar) findViewById(R.id.pbProgress_AVP);
        mProgressViewWhite = (ProgressViewWhite) findViewById(R.id.pvwProgressLoadingVideo_AVP);
        View view = findViewById(R.id.ivBack_AVP);
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    clickBack();
                }
            });
        }

        if (this.mPlayPause != null){
            mPlayPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    playPauseChange(buttonView, isChecked);
                }
            });
        }

        startPlayer();

    }

    protected void clickBack() {
        finish();
    }


    private void playPauseChange(CompoundButton button, boolean isChecked) {
        if (!isChecked) {
            this.isPauseAfter = true;
            this.mVideoView.pause();
            showAnimation();
        } else if (this.isPreparing || !this.isPrepared) {
            this.isPauseAfter = false;
            prepareVideoPlayerIfNeed();
        } else {
            this.mVideoView.start();
        }
    }

    private void showAnimation() {
        this.mTopContainer.animate().cancel();
        this.mTopContainer.setAlpha(1.0f);
        this.mTopContainer.setTranslationY(0.0f);
        this.mBottomContainer.animate().cancel();
        this.mBottomContainer.setAlpha(1.0f);
        this.mBottomContainer.setTranslationY(0.0f);
    }


    private void prepareVideoPlayerIfNeed() {
        isPreparing = true;
        isPrepared = false;
        mVideoView.setVideoURI(Uri.parse(mVideoForPlaying.files.getQuality(mQuality)));
        mVideoView.requestFocus();
        mVideoView.start();
        mProgressViewWhite.setVisibility(View.VISIBLE);
        mProgressViewWhite.startIndeterminateAnimation();
    }


    private void startPlayer() {
        mTitle.setText(mVideoForPlaying.title);
        mSubTitle.setText(mVideoForPlaying.getOwnerName() + " | " + mQuality + "p");
        mProgressTimeView.setProgressView(mProgressVideoView, this);
        setTime(mLeftTime, 0);
        setTime(mRightTime, mVideoForPlaying.duration);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnErrorListener(this);
        mPlayPause.setChecked(true);
    }

    private void setTime(TextView timeView, int time) {
        timeView.setText(VideoUIUtils.getDuration(time));
    }


    public static IntentBuilder_ intent(Context context) {
        return new IntentBuilder_(context);
    }

    public static IntentBuilder_ intent(android.app.Fragment fragment) {
        return new IntentBuilder_(fragment);
    }

    public static IntentBuilder_ intent(Fragment supportFragment) {
        return new IntentBuilder_(supportFragment);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mHanlerPlayingTracker.post(mRunnableTiming);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.mPlayPause.isChecked()) {
            onClickPP();
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        mProgressVideoView.setSecondaryProgress((int) (((float) mProgressVideoView.getMax()) * (((float) percent) / 100.0f)));
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mCurrentTime = 0;
        mVideoView.seekTo(mCurrentTime);
        mProgressVideoView.setProgress(mCurrentTime);
        mPlayPause.setChecked(false);
        setTime(mLeftTime, 0);
        setTime(mRightTime, mProgressVideoView.getMax() / CredentialsApi.ACTIVITY_RESULT_ADD_ACCOUNT);

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        JustToast.makeAndShow(this, getString(R.string.cannot_open_this_video), 1);
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setOnBufferingUpdateListener(this);
        isPreparing = false;
        isPrepared = true;
        mProgressVideoView.setMax(mp.getDuration());
        mProgressVideoView.setProgress(0);
        mProgressVideoView.setSecondaryProgress(0);
        if (isPauseAfter) {
            mVideoView.pause();
        } else {
            pauseListenProgressVideo(true);
        }
    }


    private void hideAnimation() {
        mTopContainer.animate().cancel();
        if (mTopContainer.getAlpha() < 1.0f) {
            mTopContainer.setAlpha(1.0f);
        }
        if (mTopContainer.getTranslationY() < 0.0f) {
            mTopContainer.setTranslationY(0.0f);
        }
        mTopContainer.animate().alpha(0.0f).translationY((float) (-mTopContainer.getHeight())).setDuration(1000).setInterpolator(new AccelerateDecelerateInterpolator()).setStartDelay(1500).start();
        mBottomContainer.animate().cancel();
        if (mBottomContainer.getAlpha() < 1.0f) {
            mBottomContainer.setAlpha(1.0f);
        }
        if (mBottomContainer.getTranslationY() > 0.0f) {
            mBottomContainer.setTranslationY(0.0f);
        }
        mBottomContainer.animate().alpha(0.0f).translationY((float) mBottomContainer.getHeight()).setDuration(1000).setInterpolator(new AccelerateDecelerateInterpolator()).setStartDelay(1500).start();
    }



    @Override
    public void onClickPP() {
        mPlayPause.setChecked(!mPlayPause.isChecked());
        mPlayPauseIntro.setAlpha(1.0f);
        mPlayPauseIntro.setScaleX(1.0f);
        mPlayPauseIntro.setScaleY(1.0f);
        mPlayPauseIntro.setImageResource(!mPlayPause.isChecked() ? R.drawable.ic_player_pause : R.drawable.ic_player_play);
        mPlayPauseIntro.animate().alpha(0.0f).scaleX(4.0f).scaleY(4.0f).setDuration(800).setInterpolator(new AccelerateInterpolator()).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JustLog.d("VPA", "onResume: set IMMERSIVE MODE");
        setImmersiveMode();
    }

    @Override
    public void pauseListenProgressVideo(boolean z) {
        isContinueListenTime = z;
        if (isContinueListenTime) {
            hideAnimation();
        } else {
            showAnimation();
        }

    }

    @Override
    public void seekToProgressValue() {
        if (this.isPrepared && Math.abs(this.mProgressVideoView.getProgress() - this.mVideoView.getCurrentPosition()) >= CredentialsApi.ACTIVITY_RESULT_ADD_ACCOUNT) {
            this.mVideoView.seekTo(this.mProgressVideoView.getProgress());
            if (!this.mPlayPause.isChecked()) {
                this.mPlayPause.setChecked(true);
            }
        }
    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
        injectExtras();
    }

    private void offDisplaySleep() {
        getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
    }


    private void injectExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(M_QUALITY_EXTRA)) {
                mQuality = extras.getInt(M_QUALITY_EXTRA);
            }
            if (extras.containsKey(M_VIDEO_FOR_PLAYING_EXTRA)) {
                mVideoForPlaying = (Video) extras.getSerializable(M_VIDEO_FOR_PLAYING_EXTRA);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }

    private Runnable mRunnableTiming = new Runnable() {
        @Override
        public void run() {
            if (mVideoView.isPlaying()){
                if (mCurrentTime == mVideoView.getCurrentPosition()){
                    mProgressViewWhite.setVisibility(View.VISIBLE);
                }else {
                    mProgressViewWhite.setVisibility(View.INVISIBLE);
                }
                mCurrentTime = mVideoView.getCurrentPosition();
            } else if (isPrepared && !mPlayPause.isChecked()){
                mProgressViewWhite.setVisibility(View.INVISIBLE);
            }
            if (isContinueListenTime){
                mProgressVideoView.setProgress(mCurrentTime);
                setTime(mLeftTime, mProgressVideoView.getProgress() / CredentialsApi.ACTIVITY_RESULT_ADD_ACCOUNT);
                setTime(mRightTime, (mProgressVideoView.getMax() - mProgressVideoView.getProgress()) / CredentialsApi.ACTIVITY_RESULT_ADD_ACCOUNT);
            }
            mHanlerPlayingTracker.postDelayed(mRunnableTiming, 500);
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            setImmersiveMode();
        }
    }

    private void setImmersiveMode() {
        getWindow().getDecorView().setSystemUiVisibility(5894);
    }
}
