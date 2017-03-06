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
import com.serjiosoft.themefrost.builder_intent.FragmentBuilder;
import com.serjiosoft.themefrost.managers.JustLog;
import com.serjiosoft.themefrost.managers.ListMoreController;
import com.serjiosoft.themefrost.themefrost_api.models_api.Video;
import com.serjiosoft.themefrost.themefrost_api.request.IVKRequest;
import com.serjiosoft.themefrost.themefrost_api.request.JustVKRequest;
import com.serjiosoft.themefrost.themefrost_api.request.VKRequestType;
import com.serjiosoft.themefrost.themefrost_api.request.VKResponseConstants;
import com.vk.sdk.api.VKApiConst;

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

    public static String M_TYPE_REQUEST_ARG = "mTypeRequest";
    public static String M_VK_PARAMETER_ARG = "mVKParameter";



    public VideoRecycleFragment() {
        mClearAfter = false;
    }


    public static class FragmentBuilder_ extends FragmentBuilder<FragmentBuilder_, VideoRecycleFragment> {
        public VideoRecycleFragment build() {
            VideoRecycleFragment fragment = new VideoRecycleFragment();
            fragment.setArguments(this.args);
            return fragment;
        }

        public FragmentBuilder_ mVKParameter(HashMap<String, Object> mVKParameter) {
            this.args.putSerializable(VideoRecycleFragment.M_VK_PARAMETER_ARG, mVKParameter);
            return this;
        }

        public FragmentBuilder_ mTypeRequest(VKRequestType mTypeRequest) {
            this.args.putSerializable(VideoRecycleFragment.M_TYPE_REQUEST_ARG, mTypeRequest);
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

        ListMoreController.DEFAULT_PARAMETER_OFFSET = mTypeRequest.equals(VKRequestType.VIDEO_GET_CATALOG_SECTION) ? (String) mVKParameter.get(VKResponseConstants.KEY_FROM) : null;
        ListMoreController.DEFAULT_OFFSET = 0;
        int i = mTypeRequest.equals(VKRequestType.VIDEO_GET_CATALOG_SECTION)?16:mTypeRequest.equals(VKRequestType.WALL_GET)?50:20;
        ListMoreController.DEFAULT_STEP = i;

        mListMoreController = new ListMoreController(ListMoreController.DEFAULT_OFFSET, ListMoreController.DEFAULT_STEP);

        initConfiguration();

        mAdapter = new VideoRecycleAdapter();
        mAdapter.setLoadMoreInterface(this);
        mRecyclerVideos.setAdapter(mAdapter);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                VideoRecycleFragment.this.mSwipeRefreshLayout.setRefreshing(true);
                VideoRecycleFragment.this.onRefresh();
            }
        });
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


    private void injectFragmentArguments(){
        Bundle args = getArguments();
        if (args != null){
            if (args.containsKey(M_VK_PARAMETER_ARG)){
                mVKParameter = (HashMap<String, Object>) args.getSerializable(M_VK_PARAMETER_ARG);
            }
            if (args.containsKey(M_TYPE_REQUEST_ARG)){
                mTypeRequest = (VKRequestType) args.getSerializable(M_TYPE_REQUEST_ARG);
            }
        }
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

    }

    protected void canLoad() {
        mEmptyLoading.setVisibility(View.GONE);
        mErrorLoading.setVisibility(View.GONE);
        if (mTypeRequest != null){
            new JustVKRequest(this, mTypeRequest).setVKParameter(mVKParameter).execute();
        }
    }

    @Override
    public void loadMore() {
        mAdapter.setIsLoading(true);
        mAdapter.notifyDataSetChanged();
        updateCountManager();
        canLoad();
    }

    private void updateCountManager() {
        if (this.mTypeRequest.equals(VKRequestType.VIDEO_GET_CATALOG_SECTION)) {
            this.mVKParameter.put(VKApiConst.COUNT, Integer.valueOf(this.mListMoreController.getCount()));
            this.mVKParameter.put(VKResponseConstants.KEY_FROM, this.mListMoreController.getParameterOffset());
            return;
        }
        this.mVKParameter.put(VKApiConst.COUNT, Integer.valueOf(this.mListMoreController.getCount()));
        this.mVKParameter.put(VKApiConst.OFFSET, Integer.valueOf(this.mListMoreController.getOffset()));
    }



    @Override
    public void onFailure(int code, String message) {
        if (isAdded()) {
            Log.e("TabMenu#1", "OnFailure load videos: " + code + " | " + message);
            if (this.mAdapter.getItemCount() == 0) {
                this.mErrorLoading.setVisibility(View.VISIBLE);
            }
            this.mSwipeRefreshLayout.setRefreshing(false);
            this.mAdapter.setIsLoading(false);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess(ArrayList<Video> result, @Nullable String parameter) {
        if(isAdded()) {
            JustLog.d("TabMenu#1", "OnSuccess load videos: " + result.size());
            if (mTypeRequest.equals(VKRequestType.VIDEO_GET_CATALOG_SECTION)){
                mListMoreController.setParameterOffset(parameter);
            } else {
                mListMoreController.setOffset(mListMoreController.getOffset() + ListMoreController.DEFAULT_STEP);
            }
            if (result.size() == 0 || (this.mTypeRequest.equals(VKRequestType.VIDEO_GET_CATALOG_SECTION) && parameter == null)){
                this.mListMoreController.setOffset(0);
                this.mListMoreController.setParameterOffset(ListMoreController.DEFAULT_PARAMETER_OFFSET);
                this.mAdapter.setLoadMoreState(false);

            }
            this.mSwipeRefreshLayout.setRefreshing(false);
            if (this.mClearAfter) {
                this.mAdapter.clearAll();
                this.mClearAfter = false;
            }
            this.mAdapter.setIsLoading(false);
            this.mAdapter.addVideos(result);
            if (this.mAdapter.getItemCount() == 0) {
                this.mEmptyLoading.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroyView() {
        contentView = null;
        super.onDestroyView();
    }
}
