package com.serjiosoft.themefrost.fragments.catalog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.themefrost_api.models_api.CatalogKid;

import java.util.ArrayList;

/**
 * Created by autoexec on 01.03.2017.
 */

public class CatalogSectionAdapter extends RecyclerView.Adapter<CatalogSectionHolder> {

    private Context mContextActivity;
    private ArrayList<CatalogKid> mData;

    public CatalogSectionAdapter(ArrayList<CatalogKid> data, Context contextActivity) {
        this.mData = data;
        this.mContextActivity = contextActivity;
    }

    public void updateContent(ArrayList<CatalogKid> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public CatalogSectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CatalogSectionHolder(LayoutInflater.from(this.mContextActivity).inflate(R.layout.item_section_video, parent, false), this.mContextActivity);
    }

    public void onBindViewHolder(CatalogSectionHolder holder, int position) {
        holder.injectCatalogable(mData.get(position), position);
    }

    public int getItemCount() {
        return this.mData.size();
    }
}
