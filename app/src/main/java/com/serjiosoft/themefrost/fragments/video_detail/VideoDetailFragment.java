package com.serjiosoft.themefrost.fragments.video_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.builder_intent.FragmentBuilder;
import com.serjiosoft.themefrost.fragments.base_classes.BaseFragment;
import com.serjiosoft.themefrost.themefrost_api.models_api.Video;

/**
 * Created by autoexec on 28.02.2017.
 */

public class VideoDetailFragment extends BaseFragment {

    private View contentView;
    public static final String M_VIDEO_ARG = "mVideo";
    private Video mVideo;

   // private Bundle args = new Bundle();

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
    }

    private void injectFragmentArguments(){
        Bundle args = getArguments();
        if (args != null && args.containsKey(M_VIDEO_ARG)){
            this.mVideo = (Video) args.getSerializable(M_VIDEO_ARG);
        }
    }
}
