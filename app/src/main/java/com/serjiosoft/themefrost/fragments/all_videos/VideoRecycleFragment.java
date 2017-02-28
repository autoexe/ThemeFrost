package com.serjiosoft.themefrost.fragments.all_videos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.managers.JustLog;
import com.serjiosoft.themefrost.managers.ListMoreController;
import com.serjiosoft.themefrost.themefrost_api.models_api.Video;
import com.serjiosoft.themefrost.themefrost_api.request.IVKRequest;
import com.serjiosoft.themefrost.themefrost_api.request.VKRequestType;

import java.util.ArrayList;
import java.util.HashMap;



/**
 * Created by autoexec on 24.02.2017.
 */

public class VideoRecycleFragment extends Fragment implements IVKRequest<ArrayList<Video>>, SwipeRefreshLayout.OnRefreshListener, LoadMoreHolder.ILoadMoreInterface {

    public static final String TAG = "VideoRecycleFragment";
    private VideoRecycleAdapter mAdapter;
    private boolean mClearAfter = false;
    private TextView mEmptyLoading;
    private TextView mErrorLoading;
    private ListMoreController mListMoreController;
    private RecyclerView mRecyclerVideos;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View contentView;

    private VKRequestType mTypeRequest;
    private HashMap<String, Object> mVKParameter;


    public VideoRecycleFragment() {
        mClearAfter = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_video_recycle, container, false);
        }

        mRecyclerVideos = (RecyclerView) contentView.findViewById(R.id.rvListVideos_FVR);
        mEmptyLoading = (TextView) contentView.findViewById(R.id.tvBlockEmpty_EWL);
        mErrorLoading = (TextView) contentView.findViewById(R.id.tvBlockError_EWL);
        mSwipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.sfl);


        return contentView;
    }

    private void startConfigure() {
        initSwipeRefreshLayout();

        ListMoreController.DEFAULT_OFFSET = 0;
        ListMoreController.DEFAULT_STEP = 6;
        mListMoreController = new ListMoreController(ListMoreController.DEFAULT_OFFSET, ListMoreController.DEFAULT_STEP);

        initConfiguration();

        mAdapter = new VideoRecycleAdapter();
        mAdapter.setLoadMoreInterface(this);
        mRecyclerVideos.setAdapter(mAdapter);
    }

    private void initConfiguration() {
        int columnCount;
        columnCount = getResources().getInteger(R.integer.column_count_portrait);
        if (this.mRecyclerVideos.getLayoutManager() == null) {
            this.mRecyclerVideos.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));
        } else {
            ((GridLayoutManager) this.mRecyclerVideos.getLayoutManager()).setSpanCount(columnCount);
        }
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getVideos() {


    }

    @Override
    public void onRefresh() {
        this.mClearAfter = true;
        this.mListMoreController.setOffset(0);
        this.mListMoreController.setParameterOffset(ListMoreController.DEFAULT_PARAMETER_OFFSET);
        this.mAdapter.setLoadMoreState(true);
        canLoad();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startConfigure();

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                VideoRecycleFragment.this.mSwipeRefreshLayout.setRefreshing(true);
                VideoRecycleFragment.this.onRefresh();
            }
        });
    }

    protected void canLoad() {
        mEmptyLoading.setVisibility(View.GONE);
        mErrorLoading.setVisibility(View.GONE);
        getVideos();
    }

    @Override
    public void loadMore() {
        mAdapter.setIsLoading(true);
        this.mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(int code, String message) {
        Log.e("TabMenu#1", "OnFailure load videos: " + code + " | " + message);
    }

    @Override
    public void onSuccess(ArrayList<Video> videos, @Nullable String str) {
        JustLog.d("TabMenu#1", "OnSuccess load videos: " + videos.size());
    }
}
