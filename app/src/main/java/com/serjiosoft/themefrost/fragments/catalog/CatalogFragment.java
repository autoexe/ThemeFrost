package com.serjiosoft.themefrost.fragments.catalog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.builder_intent.FragmentBuilder;
import com.serjiosoft.themefrost.fragments.all_videos.LoadMoreHolder;
import com.serjiosoft.themefrost.fragments.base_classes.BaseFragment;
import com.serjiosoft.themefrost.managers.JustLog;
import com.serjiosoft.themefrost.managers.ListMoreController;
import com.serjiosoft.themefrost.themefrost_api.models_api.Catalog;
import com.serjiosoft.themefrost.themefrost_api.request.IVKRequest;
import com.serjiosoft.themefrost.themefrost_api.request.JustVKRequest;
import com.serjiosoft.themefrost.themefrost_api.request.VKRequestType;
import com.serjiosoft.themefrost.themefrost_api.request.VKResponseConstants;
import com.vk.sdk.api.VKApiConst;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by autoexec on 01.03.2017.
 */

public class CatalogFragment extends BaseFragment implements IVKRequest<ArrayList<Catalog>>, SwipeRefreshLayout.OnRefreshListener, LoadMoreHolder.ILoadMoreInterface {

    public static final String M_TYPE_REQUEST_ARG = "mTypeRequest";
    public static final String M_VK_PARAMETER_ARG = "mVKParameter";
    public static final String TITLE_ARG = "title";
    private View contentView;

    private CatalogAdapter mCatalogAdapter;
    private boolean mClearAfter = false;
    protected TextView mErrorBlock;
    private ListMoreController mListMoreController;
    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected TextView mTextEmpty;
    protected Toolbar mToolbar;
    protected VKRequestType mTypeRequest;
    protected HashMap<String, Object> mVKParameter;
    protected String title;



    public static class FragmentBuilder_ extends FragmentBuilder<FragmentBuilder_, CatalogFragment> {
        public CatalogFragment build() {
            CatalogFragment fragment = new CatalogFragment();
            fragment.setArguments(this.args);
            return fragment;
        }

        public FragmentBuilder_ title(String title) {
            args.putString(CatalogFragment.TITLE_ARG, title);
            return this;
        }

        public FragmentBuilder_ mVKParameter(HashMap<String, Object> mVKParameter) {
            args.putSerializable(CatalogFragment.M_VK_PARAMETER_ARG, mVKParameter);
            return this;
        }

        public FragmentBuilder_ mTypeRequest(VKRequestType mTypeRequest) {
            args.putSerializable(CatalogFragment.M_TYPE_REQUEST_ARG, mTypeRequest);
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
        contentView = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_catalog, container, false);
        }
        return this.contentView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mErrorBlock = (TextView) contentView.findViewById(R.id.tvBlockError_EWL);
        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.flListVideosFromWall_FC);
        mSwipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.sfl);
        mTextEmpty = (TextView) contentView.findViewById(R.id.tvBlockEmpty_EWL);
        mToolbar = (Toolbar) contentView.findViewById(R.id.toolbar);

        initializeViews();
    }

    @Override
    public void onDestroyView() {
        contentView = null;
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        mClearAfter = true;
        mListMoreController.setOffset(0);
        mListMoreController.setParameterOffset(null);
        updateCountManager();
        mCatalogAdapter.setLoadMoreState(true);
        canLoad();

    }

    private void updateCountManager() {
        if (mTypeRequest.equals(VKRequestType.NEWS_FEED_GET)) {
            mVKParameter.put(VKApiConst.COUNT, Integer.valueOf(mListMoreController.getCount()));
            mVKParameter.put("start_from", mListMoreController.getParameterOffset());
            return;
        }
        mVKParameter.put(VKApiConst.COUNT, Integer.valueOf(this.mListMoreController.getCount()));
        mVKParameter.put(VKResponseConstants.KEY_FROM, this.mListMoreController.getParameterOffset());
    }


    @Override
    public void onFailure(int code, String message) {
        if (!ignoreResponseIfNeeded()) {
            Log.e("Catalog", "OnFailure load videos: " + code + " | " + message);
            this.mSwipeRefreshLayout.setRefreshing(false);
            if (this.mCatalogAdapter.getItemCount() == 0) {
                this.mErrorBlock.setVisibility(View.VISIBLE);
            }
            this.mCatalogAdapter.setIsLoading(false);
            this.mCatalogAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onSuccess(ArrayList<Catalog> result, @Nullable String parameter) {
        if (!ignoreResponseIfNeeded()) {
            JustLog.d("Catalog", "OnSuccess load catalogs: " + result.size());
            mSwipeRefreshLayout.setRefreshing(false);
            mListMoreController.setParameterOffset(parameter);
            if (result.size() == 0 || TextUtils.isEmpty(parameter)) {
                mListMoreController.setParameterOffset(null);
                mCatalogAdapter.setLoadMoreState(false);
            }
            if (result.size() == 0 && mCatalogAdapter.getItemCount() == 0) {
                mTextEmpty.setVisibility(View.VISIBLE);
            }
            if (mClearAfter) {
                mCatalogAdapter.clearAll();
                mClearAfter = false;
            }
            mCatalogAdapter.setIsLoading(false);
            mCatalogAdapter.addCatalogs(result);
        }

    }

    @Override
    public void loadMore() {

    }

    private void injectFragmentArguments() {
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(TITLE_ARG)) {
                title = args.getString(TITLE_ARG);
            }
            if (args.containsKey(M_VK_PARAMETER_ARG)) {
                mVKParameter = (HashMap) args.getSerializable(M_VK_PARAMETER_ARG);
            }
            if (args.containsKey(M_TYPE_REQUEST_ARG)) {
                mTypeRequest = (VKRequestType) args.getSerializable(M_TYPE_REQUEST_ARG);
            }
        }
    }

    private void initializeViews() {
        mToolbar.setTitle(title);
        initializeToolbar(mToolbar);
        startConfigure();
    }

    private void startConfigure(){
        initSwipeRefreshLayout();
        ListMoreController.DEFAULT_OFFSET = 0;
        ListMoreController.DEFAULT_STEP = 10;
        mListMoreController = new ListMoreController(ListMoreController.DEFAULT_OFFSET, ListMoreController.DEFAULT_STEP);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, 1, false));
        mCatalogAdapter = new CatalogAdapter(this.mActivity);
        mCatalogAdapter.setLoadMoreInterface(this);
        mRecyclerView.setAdapter(this.mCatalogAdapter);
        mSwipeRefreshLayout.post(new Runnable() {
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    private void initSwipeRefreshLayout() {
        getContext().getTheme().resolveAttribute(R.attr.colorActionBar, new TypedValue(), true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
       // this.mSwipeRefreshLayout.setColorSchemeColors(mTypedValue.data);
    }

    protected void canLoad() {
        mTextEmpty.setVisibility(View.GONE);
        mErrorBlock.setVisibility(View.GONE);
        new JustVKRequest(this, mTypeRequest).setVKParameter(mVKParameter).execute();
    }

}
