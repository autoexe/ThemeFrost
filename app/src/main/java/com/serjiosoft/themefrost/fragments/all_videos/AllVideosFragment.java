package com.serjiosoft.themefrost.fragments.all_videos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.builder_intent.FragmentBuilder;
import com.serjiosoft.themefrost.fragments.base_classes.BaseFragment;
import com.serjiosoft.themefrost.managers.UserController;
import com.serjiosoft.themefrost.themefrost_api.request.VKRequestType;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.methods.VKApiWall;

/**
 * Created by autoexec on 24.02.2017.
 */

public class AllVideosFragment extends BaseFragment {

    private View contentView;
    protected Toolbar mToolbar;
    protected TabLayout mTabsLayout;
    protected ViewPager mPagerVideos;
    private VideosPagerAdapter mPagerVideosAdapter;


    public static class FragmentBuilder_ extends FragmentBuilder<FragmentBuilder_, AllVideosFragment> {
        public AllVideosFragment build() {
            AllVideosFragment fragment = new AllVideosFragment();
            fragment.setArguments(this.args);
            return fragment;
        }
    }

    public static FragmentBuilder_ builder() {
        return new FragmentBuilder_();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = super.onCreateView(inflater, container, savedInstanceState);
        if(contentView == null){
            contentView = inflater.inflate(R.layout.fragment_videos, container, false);
        }
        return  contentView;
    }

    @Override
    public void onDestroyView() {
        contentView = null;
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPagerVideos = (ViewPager) contentView.findViewById(R.id.vpMyVideosPager_FV);
        mTabsLayout = (TabLayout) contentView.findViewById(R.id.tlVideoTabs_FV);
        mToolbar = (Toolbar) contentView.findViewById(R.id.toolbar);



        mToolbar.setTitle(R.string.my_video);
        initializeToolbar(mToolbar);
        initializePager();
        initializeTabs();
    }

    private void initializePager(){
        mPagerVideosAdapter = new VideosPagerAdapter(getChildFragmentManager());
        mPagerVideosAdapter.addFragment(VideoRecycleFragment.builder().mTypeRequest(VKRequestType.VIDEO_GET)
                .mVKParameter(VKParameters.from(VKApiConst.OWNER_ID, Integer.valueOf(UserController.getUser(mActivity).id)
                        , VKApiWall.EXTENDED, Integer.valueOf(1))).build(), getString(R.string.my_video_added));

        mPagerVideosAdapter.addFragment(VideoRecycleFragment.builder()
                .mTypeRequest(VKRequestType.VIDEO_GET_USER_VIDEOS)
                .mVKParameter(VKParameters.from(VKApiConst.USER_ID, Integer.valueOf(UserController.getUser(mActivity).id)
                        , VKApiWall.EXTENDED, Integer.valueOf(1))).build(), getString(R.string.my_video_with_me));


        mPagerVideos.setAdapter(mPagerVideosAdapter);

    }

    private void initializeTabs() {
        mTabsLayout.setupWithViewPager(mPagerVideos);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
