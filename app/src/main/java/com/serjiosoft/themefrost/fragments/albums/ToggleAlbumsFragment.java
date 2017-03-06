package com.serjiosoft.themefrost.fragments.albums;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.builder_intent.FragmentBuilder;
import com.serjiosoft.themefrost.custom_views.JustToast;
import com.serjiosoft.themefrost.custom_views.progresses.ProgressView;
import com.serjiosoft.themefrost.fragments.VideosFragment;
import com.serjiosoft.themefrost.fragments.all_videos.VideoRecycleFragment;
import com.serjiosoft.themefrost.fragments.all_videos.VideosPagerAdapter;
import com.serjiosoft.themefrost.fragments.base_classes.BaseFragment;
import com.serjiosoft.themefrost.themefrost_api.models_api.Album;
import com.serjiosoft.themefrost.themefrost_api.request.IVKRequest;
import com.serjiosoft.themefrost.themefrost_api.request.JustVKRequest;
import com.serjiosoft.themefrost.themefrost_api.request.VKRequestType;
import com.serjiosoft.themefrost.themefrost_api.request.VKResponseConstants;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.methods.VKApiWall;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by autoexec on 01.03.2017.
 */

public class ToggleAlbumsFragment extends BaseFragment implements IVKRequest<ArrayList<Album>> {

    public static final String OWNER_ID_ARG = "ownerId";
    public static final String TITLE_ARG = "title";
    private View contentView;

    private Boolean isSystemAlbums = Boolean.valueOf(false);
    private ArrayList<Album> mAlbums;
    protected TextView mErrorBlock;
    protected FloatingActionButton mFABChangeAlbums;
    protected TextView mLoadAgain;
    protected ViewPager mPagerVideos;
    private VideosPagerAdapter mPagerVideosAdapter;
    protected ProgressView mProgressView;
    private ArrayList<Album> mSystemAlbums;
    protected TabLayout mTabsLayout;
    protected TextView mTextEmpty;
    protected Toolbar mToolbar;
    protected int ownerId;
    protected String title;


    public static class FragmentBuilder_ extends FragmentBuilder<FragmentBuilder_, ToggleAlbumsFragment> {
        public ToggleAlbumsFragment build() {
            ToggleAlbumsFragment fragment = new ToggleAlbumsFragment();
            fragment.setArguments(this.args);
            return fragment;
        }

        public FragmentBuilder_ ownerId(int ownerId) {
            this.args.putInt(ToggleAlbumsFragment.OWNER_ID_ARG, ownerId);
            return this;
        }

        public FragmentBuilder_ title(String title) {
            this.args.putString(ToggleAlbumsFragment.TITLE_ARG, title);
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
            this.contentView = inflater.inflate(R.layout.fragment_toggle_albums, container, false);
        }
        return this.contentView;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mLoadAgain = (TextView) contentView.findViewById(R.id.tvLoadAgain_EWL);
        this.mFABChangeAlbums = (FloatingActionButton) contentView.findViewById(R.id.fabChangeAlbums_FTA);
        this.mErrorBlock = (TextView) contentView.findViewById(R.id.tvBlockError_EWL);
        this.mToolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        this.mProgressView = (ProgressView) contentView.findViewById(R.id.pvProgress_FA);
        this.mTabsLayout = (TabLayout) contentView.findViewById(R.id.tlVideoTabs_FA);
        this.mPagerVideos = (ViewPager) contentView.findViewById(R.id.vpMyVideosPager_FA);
        this.mTextEmpty = (TextView) contentView.findViewById(R.id.tvBlockEmpty_EWL);
        if (this.mLoadAgain != null) {
            this.mLoadAgain.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    clickReload();
                }
            });
        }
        if (this.mFABChangeAlbums != null) {
            this.mFABChangeAlbums.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    changeAlbumsClick();
                }
            });
            this.mFABChangeAlbums.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View view) {
                    changeAlbumsLongClick();
                    return true;
                }
            });
        }
        initializeViews();

    }


    private void clickReload() {
        canLoadAlbums();
    }

    private void changeAlbumsClick() {
        actionChangeAlbum();
        if (this.mFABChangeAlbums.getVisibility() == View.VISIBLE) {
            this.mFABChangeAlbums.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                public void onHidden(FloatingActionButton fab) {
                    super.onHidden(fab);
                    ToggleAlbumsFragment.this.mFABChangeAlbums.setImageResource(ToggleAlbumsFragment.this.isSystemAlbums.booleanValue() ? R.drawable.ic_albums : R.drawable.ic_my_video);
                    ToggleAlbumsFragment.this.mFABChangeAlbums.show();
                }
            });
        }
    }


    private void changeAlbumsLongClick() {
        if (this.isSystemAlbums.booleanValue()) {
            JustToast.makeAndShow(this.mActivity, getString(R.string.albums), 0);
        } else {
            JustToast.makeAndShow(this.mActivity, getString(R.string.videos), 0);
        }
    }


    private void actionChangeAlbum() {
        this.isSystemAlbums = Boolean.valueOf(!this.isSystemAlbums.booleanValue());
        if (this.isSystemAlbums.booleanValue()) {
            initializePager(this.mSystemAlbums);
        } else {
            initializePager(this.mAlbums);
        }
        initializeTabs();
    }


    private void initializePager(ArrayList<Album> result) {
        this.mPagerVideosAdapter = new VideosPagerAdapter(getChildFragmentManager());
        Iterator i$ = result.iterator();
        while (i$.hasNext()) {
            Album album = (Album) i$.next();
            this.mPagerVideosAdapter.addFragment(VideoRecycleFragment.builder().mTypeRequest(VKRequestType.VIDEO_GET)
                    .mVKParameter(VKParameters.from(VKApiConst.OWNER_ID, Integer.valueOf(this.ownerId),
                            VKApiConst.ALBUM_ID, Integer.valueOf(album.id), VKApiWall.EXTENDED, Integer.valueOf(1)))
                    .build(), album.title);
        }
        this.mPagerVideos.setAdapter(this.mPagerVideosAdapter);
    }



    private void initializeTabs() {
        this.mTabsLayout.setVisibility(View.VISIBLE);
        this.mTabsLayout.setupWithViewPager(this.mPagerVideos);
    }



    private void separateSystemAlbums(ArrayList<Album> result) {
        this.mAlbums = new ArrayList();
        this.mSystemAlbums = new ArrayList();
        Iterator i$ = result.iterator();
        while (i$.hasNext()) {
            Album album = (Album) i$.next();
            if (album.isSystem()) {
                if (album.count > 0) {
                    this.mSystemAlbums.add(album);
                }
            } else if (album.count > 0) {
                this.mAlbums.add(album);
            }
        }
    }

    public void sendMessage(Object value) {
        super.sendMessage(value);
        if ((value instanceof Integer) && ((Integer) value).intValue() == R.id.action_wall) {
            this.mActivity.nextFragment(VideosFragment.builder().title(getString(R.string.wall))
                    .subTitle(this.title).mTypeRequest(VKRequestType.WALL_GET)
                    .mVKParameter(VKParameters.from(VKApiConst.OWNER_ID, Integer.valueOf(this.ownerId),
                            VKApiWall.EXTENDED, Integer.valueOf(1), VKResponseConstants.KEY_FILTER, VKResponseConstants.KEY_ALL))
                    .build());
        }
    }



    private void initializeViews() {
        initializeMenuResource(R.menu.owner_wall_menu);
        this.mToolbar.setTitle(this.title);
        initializeToolbar(this.mToolbar);
        this.mTextEmpty.setText(R.string.album_empty);
        canLoadAlbums();
    }


    private void canLoadAlbums() {
        this.mTabsLayout.setVisibility(View.GONE);
        this.mTextEmpty.setVisibility(View.GONE);
        this.mErrorBlock.setVisibility(View.GONE);
        this.mLoadAgain.setVisibility(View.GONE);
        this.mFABChangeAlbums.setVisibility(View.GONE);
        this.mProgressView.setVisibility(View.VISIBLE);
        this.mProgressView.startIndeterminateAnimation();
        new JustVKRequest(this, VKRequestType.VIDEO_GET_ALBUMS)
                .setVKParameter(VKParameters.from(VKApiConst.OWNER_ID, Integer.valueOf(this.ownerId),
                        VKApiWall.EXTENDED, Integer.valueOf(1), VKResponseConstants.KEY_NEED_SYSTEM, Integer.valueOf(1)))
                .execute();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mProgressView.getVisibility() == View.VISIBLE) {
            mProgressView.startIndeterminateAnimation();
        }

    }

    @Override
    public void onDestroyView() {
        contentView = null;
        super.onDestroyView();
    }

    @Override
    public void onFailure(int i, String str) {
        if (!ignoreResponseIfNeeded()) {
            this.mProgressView.setVisibility(View.GONE);
            this.mErrorBlock.setVisibility(View.VISIBLE);
            this.mLoadAgain.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onSuccess(ArrayList<Album> result, @Nullable String str) {
        if (!ignoreResponseIfNeeded()) {
            this.mProgressView.setVisibility(View.GONE);
            separateSystemAlbums(result);
            if (this.mSystemAlbums.size() == 0) {
                this.mTextEmpty.setVisibility(View.VISIBLE);
                return;
            }
            if (this.mAlbums.size() > 0) {
                this.mFABChangeAlbums.setVisibility(0);
            }
            actionChangeAlbum();
        }

    }

    private void injectFragmentArguments() {
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(OWNER_ID_ARG)) {
                this.ownerId = args.getInt(OWNER_ID_ARG);
            }
            if (args.containsKey(TITLE_ARG)) {
                this.title = args.getString(TITLE_ARG);
            }
        }
    }

}
