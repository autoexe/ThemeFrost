package com.serjiosoft.themefrost.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.builder_intent.FragmentBuilder;
import com.serjiosoft.themefrost.fragments.all_videos.VideoRecycleFragment;
import com.serjiosoft.themefrost.fragments.base_classes.BaseFragment;
import com.serjiosoft.themefrost.themefrost_api.request.VKRequestType;

import java.util.HashMap;

/**
 * Created by autoexec on 01.03.2017.
 */

public class VideosFragment extends BaseFragment {

    public static final String M_TYPE_REQUEST_ARG = "mTypeRequest";
    public static final String M_VK_PARAMETER_ARG = "mVKParameter";
    public static final String SUB_TITLE_ARG = "subTitle";
    public static final String TITLE_ARG = "title";
    private View contentView;

    protected Toolbar mToolbar;
    protected VKRequestType mTypeRequest;
    protected HashMap<String, Object> mVKParameter;
    protected String subTitle;
    protected String title;



    public static class FragmentBuilder_ extends FragmentBuilder<FragmentBuilder_, VideosFragment> {
        public VideosFragment build() {
            VideosFragment fragment = new VideosFragment();
            fragment.setArguments(this.args);
            return fragment;
        }

        public FragmentBuilder_ title(String title) {
            this.args.putString(VideosFragment.TITLE_ARG, title);
            return this;
        }

        public FragmentBuilder_ subTitle(String subTitle) {
            this.args.putString(VideosFragment.SUB_TITLE_ARG, subTitle);
            return this;
        }

        public FragmentBuilder_ mTypeRequest(VKRequestType mTypeRequest) {
            this.args.putSerializable(VideosFragment.M_TYPE_REQUEST_ARG, mTypeRequest);
            return this;
        }

        public FragmentBuilder_ mVKParameter(HashMap<String, Object> mVKParameter) {
            this.args.putSerializable(VideosFragment.M_VK_PARAMETER_ARG, mVKParameter);
            return this;
        }
    }

    public static FragmentBuilder_ builder() {
        return new FragmentBuilder_();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        injectFragmentArguments();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.contentView = super.onCreateView(inflater, container, savedInstanceState);
        if (this.contentView == null) {
            this.contentView = inflater.inflate(R.layout.fragment_videos_without_tabs, container, false);
        }
        return this.contentView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mToolbar = (Toolbar) contentView.findViewById(R.id.toolbar);

        initializeViews();
    }

    protected void initializeViews() {
        this.mToolbar.setTitle(this.title);
        if (this.subTitle != null) {
            this.mToolbar.setSubtitle(this.subTitle);
        }
        initializeToolbar(this.mToolbar);
        injectFragmentVideos();
    }

    private void injectFragmentVideos() {
        getChildFragmentManager().beginTransaction().replace(R.id.flListVideosFromWall_FWV, VideoRecycleFragment.builder()
                .mTypeRequest(this.mTypeRequest).mVKParameter(this.mVKParameter).build()).commitAllowingStateLoss();
    }


    @Override
    public void onDestroyView() {
        contentView = null;
        super.onDestroyView();
    }

    private void injectFragmentArguments() {
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(TITLE_ARG)) {
                this.title = args.getString(TITLE_ARG);
            }
            if (args.containsKey(SUB_TITLE_ARG)) {
                this.subTitle = args.getString(SUB_TITLE_ARG);
            }
            if (args.containsKey(M_TYPE_REQUEST_ARG)) {
                this.mTypeRequest = (VKRequestType) args.getSerializable(M_TYPE_REQUEST_ARG);
            }
            if (args.containsKey(M_VK_PARAMETER_ARG)) {
                this.mVKParameter = (HashMap) args.getSerializable(M_VK_PARAMETER_ARG);
            }
        }
    }

}
