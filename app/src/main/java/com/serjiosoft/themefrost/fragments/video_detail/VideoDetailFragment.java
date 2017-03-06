package com.serjiosoft.themefrost.fragments.video_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.serjiosoft.themefrost.BuildConfig;
import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.builder_intent.FragmentBuilder;
import com.serjiosoft.themefrost.fragments.VideoUIUtils;
import com.serjiosoft.themefrost.fragments.all_videos.VideoRecycleHolder;
import com.serjiosoft.themefrost.fragments.base_classes.BaseFragment;
import com.serjiosoft.themefrost.managers.picasso.CircleTransform;
import com.serjiosoft.themefrost.themefrost_api.models_api.Video;
import com.serjiosoft.themefrost.themefrost_api.request.IVKRequest;
import com.serjiosoft.themefrost.themefrost_api.request.JustVKRequest;
import com.serjiosoft.themefrost.themefrost_api.request.VKRequestType;
import com.serjiosoft.themefrost.themefrost_api.request.VKResponseConstants;
import com.serjiosoft.themefrost.video_player.VideoPlayerNavigator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.vk.sdk.api.methods.VKApiWall;

import java.util.ArrayList;


/**
 * Created by autoexec on 28.02.2017.
 */

public class VideoDetailFragment extends BaseFragment {

    private View contentView;
    public static final String M_VIDEO_ARG = "mVideo";
    private Video mVideo;

    private Boolean isLiked;
    private boolean loadingLikeStatus = false;
    protected AppBarLayout mAppBarLayout;
    protected Button mButtonShareMeToWall;
    protected CollapsingToolbarLayout mColapsingToolbar;
    protected TextView mCountSee;
    protected TextView mDateAddd;
    protected TextView mDescriptionVideo;
    protected TextView mDurationView;
    protected ImageView mLikeImage;
    protected ImageView mOwnerImage;
    protected TextView mTitleOwner;
    protected TextView mTitleVideo;
    protected Toolbar mToolbar;
    protected ImageView mVideoPreviewImage;
    protected FloatingActionButton pplayButton;




    public static class FragmentBuilder_ extends FragmentBuilder<FragmentBuilder_, VideoDetailFragment> {

        public VideoDetailFragment build(){
            VideoDetailFragment fragment = new VideoDetailFragment();
            fragment.setArguments(args);

            return fragment;
        }

        public FragmentBuilder_ mVideo(Video mVideo) {
            args.putSerializable(VideoDetailFragment.M_VIDEO_ARG, mVideo);
            return this;
        }
    }


    public static FragmentBuilder_ builder() {
        return new FragmentBuilder_();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectFragmentArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_video_detail, container, false);
        }
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitleOwner = (TextView) contentView.findViewById(R.id.tvTitleOwner_VDI);
        mOwnerImage = (ImageView) contentView.findViewById(R.id.ivIconOwner_VDI);
        mLikeImage = (ImageView) contentView.findViewById(R.id.ivLikeImage_VDI);
        mCountSee = (TextView) contentView.findViewById(R.id.tvCountSee_VDI);
        mDateAddd = (TextView) contentView.findViewById(R.id.tvDateAdded_VDI);
        mAppBarLayout = (AppBarLayout) contentView.findViewById(R.id.appbar);
        mDurationView = (TextView) contentView.findViewById(R.id.tvDurationVideo_FVD);
        mButtonShareMeToWall = (Button) contentView.findViewById(R.id.btnAddToMe_VDI);
        mColapsingToolbar = (CollapsingToolbarLayout) contentView.findViewById(R.id.htab_collapse_toolbar);
        mVideoPreviewImage = (ImageView) contentView.findViewById(R.id.ivVideoPreview_FVD);
        mToolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        mTitleVideo = (TextView) contentView.findViewById(R.id.tvTitleVideo_VDI);
        pplayButton = (FloatingActionButton) contentView.findViewById(R.id.fabPlayVideo_FVD);
        mDescriptionVideo = (TextView) contentView.findViewById(R.id.tvDescription_VDI);

        configUI();
        updateVideoIfNeeded();

        if (pplayButton != null){
            pplayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playVideo();
                }
            });
        }
    }

    private void updateVideoIfNeeded() {
        if (mVideo.player == null){
            pplayButton.setVisibility(View.GONE);
            new JustVKRequest(new IVKRequest() {
                public void onSuccess(Object result, @Nullable String parameter) {
                    if (result != null) {
                        ArrayList<Video> videos = (ArrayList) result;
                        if (videos.size() > 0) {
                            VideoDetailFragment.this.mVideo = (Video) videos.get(0);
                            if (VideoDetailFragment.this.mVideo.player != null) {
                                VideoDetailFragment.this.pplayButton.setVisibility(0);
                            }
                        }
                    }
                }

                public void onFailure(int code, String message) {
                }
            }, VKRequestType.VIDEO_GET).putVKParameter(VKResponseConstants.KEY_VIDEOS, this.mVideo.owner_id + "_" + this.mVideo.id).putVKParameter(VKApiWall.EXTENDED, Integer.valueOf(1)).execute();
        }
    }

    private void configUI(){
        initializeMenuResource(R.menu.video_detail_menu);
        mToolbar.setTitle(BuildConfig.VERSION_NAME);
        mColapsingToolbar.setTitleEnabled(false);
        initializeToolbar(mToolbar);
        updateUI(mVideo);
    }

    private void updateUI(Video video) {
        int i = R.drawable.default_group;
        int i2 = R.drawable.default_friend;
        mTitleVideo.setText(video.title);
        mTitleOwner.setText(video.getOwnerName());
        mCountSee.setText(this.mActivity.getString(VideoUIUtils.getSeeTitleRes(this.mVideo.views), new Object[]{VideoRecycleHolder.COUNT_FORMATTER.format((long) this.mVideo.views)}));
        mDateAddd.setText(DateUtils.getRelativeDateTimeString(this.mActivity, video.getOwnerDateAdded() * 1000, 60000, 604800000, 0));
        if (mVideo.duration == 0) {
            mDurationView.setVisibility(View.GONE);
        } else {
            mDurationView.setVisibility(View.VISIBLE);
            mDurationView.setText(VideoUIUtils.getDuration(video.duration));
        }
        if (TextUtils.isEmpty(video.description)) {
            mDescriptionVideo.setVisibility(View.GONE);
        } else {
            mDescriptionVideo.setText(video.description);
        }
        if (TextUtils.isEmpty(video.getOwnerUrlPhoto())){
            ImageView imageView = mOwnerImage;
            if (video.profile == null){
                i2 = R.drawable.default_group;
            }
            imageView.setImageResource(i2);
        } else {
            int i3;
            RequestCreator load = Picasso.with(this.mActivity).load(video.getOwnerUrlPhoto());
            if (video.group != null){
                i3 = R.drawable.default_group;
            } else {
                i3 = R.drawable.default_friend;
            }
            RequestCreator placeholder = load.placeholder(i3);
            if (video.group == null){
                i = R.drawable.default_friend;
            }
            placeholder.error(i).transform(new CircleTransform()).into(this.mOwnerImage);
        }
        if (!TextUtils.isEmpty(video.getMaxPreviewPhoto())){
            Picasso.with(this.mActivity).load(video.getMaxPreviewPhoto()).placeholder(null).into(this.mVideoPreviewImage);
        }
        checkIsLike();
    }

    private void checkIsLike() {
    }

    private void injectFragmentArguments(){
        Bundle args = getArguments();
        if (args != null && args.containsKey(M_VIDEO_ARG)){
            mVideo = (Video) args.getSerializable(M_VIDEO_ARG);
        }
    }

    @Override
    public void onDestroyView() {
        contentView = null;
        super.onDestroyView();
    }

    protected void playVideo(){
        VideoPlayerNavigator.showVideo(mActivity, mVideo);

    }
}
