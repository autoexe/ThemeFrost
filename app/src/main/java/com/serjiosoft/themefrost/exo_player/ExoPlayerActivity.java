package com.serjiosoft.themefrost.exo_player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.View;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.serjiosoft.themefrost.BaseActivity;
import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.builder_intent.ActivityIntentBuilder;
import com.serjiosoft.themefrost.builder_intent.PostActivityStarter;
import com.serjiosoft.themefrost.themefrost_api.models_api.Video;

/**
 * Created by autoexec on 02.03.2017.
 */

public class ExoPlayerActivity extends BaseActivity implements ExoPlayer.EventListener, View.OnClickListener, PlaybackControlView.VisibilityListener {


    public static final String M_QUALITY_EXTRA = "mQuality";
    public static final String M_VIDEO_FOR_PLAYING_EXTRA = "mVideoForPlaying";
    protected int mQuality;
    protected Video mVideoForPlaying;

    private Handler mainHandler;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private static final DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onVisibilityChange(int visibility) {

    }


    public static class IntentBuilder_ extends ActivityIntentBuilder<ExoPlayerActivity.IntentBuilder_> {

        private Fragment fragmentSupport;
        private android.app.Fragment fragment;

        public IntentBuilder_(Context context) {
            super(context, ExoPlayerActivity.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), ExoPlayerActivity.class);
            this.fragment = fragment;
        }

        public IntentBuilder_(Fragment fragment) {
            super(fragment.getActivity(), ExoPlayerActivity.class);
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

        public ExoPlayerActivity.IntentBuilder_ mQuality(int mQuality) {
            return super.extra(ExoPlayerActivity.M_QUALITY_EXTRA, mQuality);
        }

        public ExoPlayerActivity.IntentBuilder_ mVideoForPlaying(Video mVideoForPlaying) {
            return super.extra(ExoPlayerActivity.M_VIDEO_FOR_PLAYING_EXTRA, mVideoForPlaying);
        }
    }

    public static ExoPlayerActivity.IntentBuilder_ intent(Context context) {
        return new ExoPlayerActivity.IntentBuilder_(context);
    }

    public static ExoPlayerActivity.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new ExoPlayerActivity.IntentBuilder_(fragment);
    }

    public static ExoPlayerActivity.IntentBuilder_ intent(Fragment supportFragment) {
        return new ExoPlayerActivity.IntentBuilder_(supportFragment);
    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exo_player_activity);

        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);

        mainHandler = new Handler();

        setVKVideo();
    }



    private void setVKVideo() {
        String userAgent = "hls";
        Uri uri = Uri.parse(mVideoForPlaying.files.getQuality(mQuality));

        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector(videoTrackSelectionFactory),
                new DefaultLoadControl());

        simpleExoPlayerView.setPlayer(player);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, bandwidthMeter,
                buildHttpDataSourceFactory(bandwidthMeter));

        MediaSource videoSource = new ExtractorMediaSource(uri, dataSourceFactory, new DefaultExtractorsFactory(), new Handler(), null);
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
    }


    private HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(Util.getUserAgent(this,
                getString(R.string.app_name)), bandwidthMeter);
    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
        injectExtras();
    }




    private void setImmersiveMode() {
        getWindow().getDecorView().setSystemUiVisibility(5894);
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
    protected void onStop() {
        super.onStop();
        if (player != null){
            player.release();
        }
    }
}
