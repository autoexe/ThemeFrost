package com.serjiosoft.themefrost.fragments.catalog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.serjiosoft.themefrost.MainActivity;
import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.custom_views.RecycleViewPicassoLight;
import com.serjiosoft.themefrost.fragments.VideosFragment;
import com.serjiosoft.themefrost.fragments.albums.ToggleAlbumsFragment;
import com.serjiosoft.themefrost.managers.picasso.CircleTransform;
import com.serjiosoft.themefrost.themefrost_api.models_api.Catalog;
import com.serjiosoft.themefrost.themefrost_api.models_api.Video;
import com.serjiosoft.themefrost.themefrost_api.request.VKRequestType;
import com.serjiosoft.themefrost.themefrost_api.request.VKResponseConstants;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.methods.VKApiWall;

import java.util.ArrayList;

/**
 * Created by autoexec on 01.03.2017.
 */

public final class CatalogHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private CatalogSectionAdapter mAdapter;
    private Catalog mCatalog;
    private Context mContextActivity;
    private TextView mMoreView;
    private RecyclerView mPagerSections;
    private ImageView mPhotoSection;
    private TextView mSubTitleSection;
    private TextView mTitleSection;


    public CatalogHolder(View itemView, Context contextActivity) {
        super(itemView);
        this.mContextActivity = contextActivity;
        this.mPhotoSection = (ImageView) itemView.findViewById(R.id.ivPhotoSection_IC);
        this.mTitleSection = (TextView) itemView.findViewById(R.id.tvTitleSection_IC);
        this.mSubTitleSection = (TextView) itemView.findViewById(R.id.tvSubtitleSection_IC);
        this.mMoreView = (TextView) itemView.findViewById(R.id.tvMore_IC);
        this.mPagerSections = (RecyclerView) itemView.findViewById(R.id.rvSections_IC);
        this.mPhotoSection.setOnClickListener(this);
        this.mTitleSection.setOnClickListener(this);
        this.mSubTitleSection.setOnClickListener(this);
        this.mMoreView.setOnClickListener(this);
        this.mPagerSections.setNestedScrollingEnabled(false);
        this.mPagerSections.setLayoutManager(new LinearLayoutManager(this.mContextActivity, 0, false));
        RecyclerView recyclerView = this.mPagerSections;
        RecyclerView.Adapter catalogSectionAdapter = new CatalogSectionAdapter(new ArrayList(), this.mContextActivity);
        this.mAdapter = (CatalogSectionAdapter) catalogSectionAdapter;
        recyclerView.setAdapter(catalogSectionAdapter);
    }

    public void updateContent(Catalog catalog) {
        this.mCatalog = catalog;
        this.mTitleSection.setText(this.mCatalog.getNameCatalog());
        loadImageForCatalog();
        showMoreIfNeeded();
        showSubtitleIfNeeded();
        this.mAdapter.updateContent(catalog.items);
        this.mPagerSections.scrollToPosition(0);
    }

    private void loadImageForCatalog() {
        String photoUrl = this.mCatalog.getPhotoUrl();
        if (TextUtils.isEmpty(photoUrl)) {
            this.mPhotoSection.setImageResource(R.drawable.default_catalog);
        } else {
            Picasso.with(this.mContextActivity).load(photoUrl).tag(RecycleViewPicassoLight.PICASSO_TAG)
                    .placeholder((int) R.drawable.default_catalog).error((int) R.drawable.default_catalog)
                    .transform(new CircleTransform()).into(this.mPhotoSection);
        }
    }

    private void showMoreIfNeeded() {
        if (this.mCatalog.next == null || this.mCatalog.items.size() <= 0 || !(this.mCatalog.items.get(0) instanceof Video)) {
            this.mMoreView.setVisibility(View.GONE);
        } else {
            this.mMoreView.setVisibility(View.VISIBLE);
        }
    }

    private void showSubtitleIfNeeded() {
        if (this.mCatalog.name != null || this.mCatalog.date <= 0) {
            this.mSubTitleSection.setVisibility(View.GONE);
            return;
        }
        this.mSubTitleSection.setText(this.mContextActivity.getString(R.string.album_video, new Object[]{Integer.valueOf(this.mCatalog.items.size())}) +
                " | " + DateUtils.getRelativeDateTimeString(this.mContextActivity, this.mCatalog.date * 1000, 60000, 604800000, 0));
        this.mSubTitleSection.setVisibility(View.VISIBLE);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPhotoSection_IC:
            case R.id.tvTitleSection_IC:
            case R.id.tvSubtitleSection_IC:
                Integer ownerId = null;
                if (this.mCatalog.group != null) {
                    ownerId = Integer.valueOf(-this.mCatalog.group.id);
                } else if (this.mCatalog.profile != null) {
                    ownerId = Integer.valueOf(this.mCatalog.profile.id);
                }
                if (ownerId != null) {
                    ((MainActivity) this.mContextActivity).nextFragment(ToggleAlbumsFragment.builder().title(this.mCatalog.getNameCatalog()).ownerId(ownerId.intValue()).build());
                    return;
                }
                return;
            case R.id.tvMore_IC :
                ((MainActivity) this.mContextActivity).nextFragment(VideosFragment.builder().title(this.mCatalog.name)
                        .mTypeRequest(VKRequestType.VIDEO_GET_CATALOG_SECTION)
                        .mVKParameter(VKParameters.from(VKResponseConstants.KEY_SECTION_ID, this.mCatalog.id, VKResponseConstants.KEY_FROM, this.mCatalog.next, VKApiWall.EXTENDED, Integer.valueOf(1)))
                        .build());
                return;
            default:
                return;
        }
    }
}
