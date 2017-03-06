package com.serjiosoft.themefrost.fragments.catalog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.fragments.all_videos.LoadMoreHolder;
import com.serjiosoft.themefrost.themefrost_api.models_api.Catalog;

import java.util.ArrayList;

/**
 * Created by autoexec on 01.03.2017.
 */

public class CatalogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean isLoadMore = true;
    private boolean isLoading = false;
    private ArrayList<Catalog> mCatalogs = new ArrayList();
    private Context mContextActivity;
    private LoadMoreHolder.ILoadMoreInterface mLoadMoreInterface;


    public CatalogAdapter(Context contextActivity) {
        this.mContextActivity = contextActivity;
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

    public void clearAll() {
        this.mCatalogs.clear();
    }

    public void addCatalogs(ArrayList<Catalog> catalogs) {
        this.mCatalogs.addAll(catalogs);
        notifyDataSetChanged();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 2) {
            return new LoadMoreHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_view, parent, false), this.mLoadMoreInterface);
        }
        return new CatalogHolder(LayoutInflater.from(this.mContextActivity).inflate(R.layout.item_catalog, parent, false), this.mContextActivity);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CatalogHolder) {
            ((CatalogHolder) holder).updateContent((Catalog) this.mCatalogs.get(position));
        } else {
            ((LoadMoreHolder) holder).setIsLoading(this.isLoading);
        }
    }

    @Override
    public int getItemCount() {
        int size = this.mCatalogs.size();
        int i = (!this.isLoadMore || this.mCatalogs.size() <= 0) ? 0 : 1;
        return i + size;
    }



    @Override
    public int getItemViewType(int position) {
        return (position == getItemCount() + -1 && this.isLoadMore) ? 2 : 1;
    }

}
