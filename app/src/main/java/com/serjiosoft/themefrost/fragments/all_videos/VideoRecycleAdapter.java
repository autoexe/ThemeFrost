package com.serjiosoft.themefrost.fragments.all_videos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.themefrost_api.models_api.Video;

import java.util.ArrayList;

/**
 * Created by autoexec on 24.02.2017.
 */

public class VideoRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private boolean isLoadMore;
    private boolean isLoading;
    private LoadMoreHolder.ILoadMoreInterface mLoadMoreInterface;
    private ArrayList<Video> mVideos;


    public VideoRecycleAdapter() {
        this.isLoadMore = true;
        this.isLoading = false;
        this.mVideos = new ArrayList();
    }

    public void setLoadMoreInterface(LoadMoreHolder.ILoadMoreInterface loadMoreInterface) {
        this.mLoadMoreInterface = loadMoreInterface;
    }

    public void setLoadMoreState(boolean value) {
        this.isLoadMore = value;
    }


    public void setIsLoading(boolean value) {
        this.isLoading = value;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 2) {
            return new LoadMoreHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_view, parent, false), mLoadMoreInterface);
        }

        return new VideoRecycleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false), parent.getContext());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VideoRecycleHolder) {
            ((VideoRecycleHolder) holder).injectVideo(mVideos.get(position), position);
        }else {
            ((LoadMoreHolder) holder).setIsLoading(isLoading);
        }
    }

    @Override
    public int getItemCount() {
        int size = mVideos.size();
        int i = (!isLoadMore || mVideos.size() <= 0) ? 0 : 1;
        return i + size;

    }


    @Override
    public int getItemViewType(int position) {
        return (position == getItemCount() + -1 && isLoadMore) ? 2 : 1;
    }


    public void addVideos(ArrayList<Video> videos) {
        mVideos.addAll(videos);
        notifyDataSetChanged();
    }

    public void clearAll() {
        mVideos.clear();
    }

}
