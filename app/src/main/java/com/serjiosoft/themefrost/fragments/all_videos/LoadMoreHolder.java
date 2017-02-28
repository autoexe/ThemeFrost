package com.serjiosoft.themefrost.fragments.all_videos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.custom_views.progresses.ProgressView;

/**
 * Created by autoexec on 24.02.2017.
 */

public class LoadMoreHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ILoadMoreInterface mLoadMoreInterface;
    private ProgressView mLoadMoreProgress;
    private TextView mTextLoadMore;


    public interface ILoadMoreInterface {
        void loadMore();
    }



    public LoadMoreHolder(View itemView, ILoadMoreInterface loadMoreInterface) {
        super(itemView);
        this.mLoadMoreInterface = loadMoreInterface;
        this.mTextLoadMore = (TextView) itemView.findViewById(R.id.tvLoadMore_LMV);
        this.mLoadMoreProgress = (ProgressView) itemView.findViewById(R.id.pvProgress_LMV);
        this.mTextLoadMore.setOnClickListener(this);

    }

    public void setIsLoading(boolean isLoading) {
        if (isLoading) {
            this.mTextLoadMore.setVisibility(View.GONE);
            this.mLoadMoreProgress.setVisibility(View.VISIBLE);
            this.mLoadMoreProgress.startIndeterminateAnimation();
            return;
        }
        this.mTextLoadMore.setVisibility(View.VISIBLE);
        this.mLoadMoreProgress.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        this.mLoadMoreInterface.loadMore();
    }
}
