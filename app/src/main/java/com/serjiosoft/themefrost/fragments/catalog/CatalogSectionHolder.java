package com.serjiosoft.themefrost.fragments.catalog;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;

import com.serjiosoft.themefrost.MainActivity;
import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.custom_views.RecycleViewPicassoLight;
import com.serjiosoft.themefrost.fragments.VideosFragment;
import com.serjiosoft.themefrost.fragments.all_videos.VideoRecycleHolder;
import com.serjiosoft.themefrost.managers.UserController;
import com.serjiosoft.themefrost.managers.picasso.CircleTransform;
import com.serjiosoft.themefrost.themefrost_api.models_api.Album;
import com.serjiosoft.themefrost.themefrost_api.models_api.CatalogKid;
import com.serjiosoft.themefrost.themefrost_api.models_api.Video;
import com.serjiosoft.themefrost.themefrost_api.request.VKRequestType;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.methods.VKApiWall;

/**
 * Created by autoexec on 01.03.2017.
 */

public final class CatalogSectionHolder extends VideoRecycleHolder {

    private CatalogKid mCatalog;

    public CatalogSectionHolder(View itemView, Context context) {
        super(itemView, context);
    }

    public void injectCatalogable(CatalogKid catalog, int position) {
        mCatalog = catalog;
        mDescription.setVisibility(View.GONE);
        if (catalog instanceof Video) {
            mCountViews.setVisibility(View.VISIBLE);
            injectVideo((Video) mCatalog, position);
            return;
        }
        mCountViews.setVisibility(View.GONE);
        injectAlbum((Album) mCatalog, position);
    }

    private void injectAlbum(Album album, int pos) {
        mTitle.setText((pos + 1) + ". " + album.title);
        mDurationView.setText(this.mContext.getString(R.string.album_video, new Object[]{COUNT_FORMATTER.format((long) album.count)}));
        Picasso.with(mContext).load(album.photo_320).placeholder(null).into(mImageVideo);
        mTitleOwner.setText(album.getOwnerName());
        mDateAdded.setText(DateUtils.getRelativeDateTimeString(mContext, album.updated_time * 1000, 60000, 604800000, 0));
        String urlPhoto = album.getOwnerUrlPhoto();
        if (urlPhoto == null && album.owner_id == UserController.getUser(this.mContext).id) {
            urlPhoto = UserController.getUser(this.mContext).photo_100;
        }
        if (urlPhoto != null) {
            Picasso.with(this.mContext).load(urlPhoto).tag(RecycleViewPicassoLight.PICASSO_TAG).placeholder( R.drawable.default_profile).error( R.drawable.default_profile).transform(new CircleTransform()).into(this.mOwnerImage);
        } else {
            this.mOwnerImage.setImageResource(R.drawable.default_profile);
        }
    }

    @Override
    public void onClick(View v) {
        if (mCatalog instanceof Album) {
            ((MainActivity) this.mContext).nextFragment(VideosFragment.builder().title(((Album) this.mCatalog).title).mTypeRequest(VKRequestType.VIDEO_GET).mVKParameter(VKParameters.from(VKApiConst.OWNER_ID, Integer.valueOf(((Album) this.mCatalog).owner_id), VKApiConst.ALBUM_ID, Integer.valueOf(((Album) this.mCatalog).id), VKApiWall.EXTENDED, Integer.valueOf(1))).build());
            return;
        }
        super.onClick(v);
    }


}

