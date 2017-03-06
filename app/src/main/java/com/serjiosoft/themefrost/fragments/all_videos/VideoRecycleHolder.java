package com.serjiosoft.themefrost.fragments.all_videos;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.serjiosoft.themefrost.MainActivity;
import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.custom_views.RecycleViewPicassoLight;
import com.serjiosoft.themefrost.fragments.VideoUIUtils;
import com.serjiosoft.themefrost.fragments.video_detail.VideoDetailFragment;
import com.serjiosoft.themefrost.managers.UserController;
import com.serjiosoft.themefrost.managers.picasso.CircleTransform;
import com.serjiosoft.themefrost.themefrost_api.models_api.Video;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

/**
 * Created by autoexec on 24.02.2017.
 */

public class VideoRecycleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final DecimalFormat COUNT_FORMATTER = new DecimalFormat("#,###,###");
    protected Context mContext;
    protected TextView mCountViews;
    protected TextView mDateAdded;
    protected TextView mDescription;
    protected TextView mDurationView;
    protected ImageView mImageVideo;
    protected ImageView mOwnerImage;
    protected TextView mTitle;
    protected TextView mTitleOwner;
    protected Video mVideo;


    public VideoRecycleHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;
        mImageVideo = (ImageView) itemView.findViewById(R.id.ivImagePreviewVideo_IV);
        mOwnerImage = (ImageView) itemView.findViewById(R.id.ivPhotoOwner_IV);
        mTitle = (TextView) itemView.findViewById(R.id.tvTitle_IV);
        mDescription = (TextView) itemView.findViewById(R.id.tvDescription_IV);
        mCountViews = (TextView) itemView.findViewById(R.id.tvCountSee_IV);
        mDurationView = (TextView) itemView.findViewById(R.id.tvDurationVideo_IV);
        mTitleOwner = (TextView) itemView.findViewById(R.id.tvNameOwner_IV);
        mDateAdded = (TextView) itemView.findViewById(R.id.tvDateAdded_IV);
        itemView.setOnClickListener(this);
    }

    public void injectVideo(Video video, int pos){
        mVideo = video;
        mTitle.setText((pos + 1) + ". " + this.mVideo.title);
        mDescription.setText(TextUtils.isEmpty(this.mVideo.description) ? this.mVideo.getOwnerName() : this.mVideo.description);
        mCountViews.setText(this.mContext.getString(VideoUIUtils.getSeeTitleRes(this.mVideo.views), new Object[]{COUNT_FORMATTER.format((long) this.mVideo.views)}));
        if (this.mVideo.duration == 0) {
            this.mDurationView.setVisibility(View.GONE);
        } else {
            this.mDurationView.setVisibility(View.VISIBLE);
            this.mDurationView.setText(VideoUIUtils.getDuration(video.duration));
        }
        Picasso.with(this.mContext).load(this.mVideo.photo_320).tag(RecycleViewPicassoLight.PICASSO_TAG).placeholder(null).into(this.mImageVideo);
        mTitleOwner.setText(this.mVideo.getOwnerName());
        mDateAdded.setText(DateUtils.getRelativeDateTimeString(this.mContext, this.mVideo.getOwnerDateAdded() * 1000, 60000, 604800000, 0));
        String urlPhoto = this.mVideo.getOwnerUrlPhoto();
        if (TextUtils.isEmpty(urlPhoto) && this.mVideo.getOwnerId() == UserController.getUser(this.mContext).id) {
            urlPhoto = UserController.getUser(this.mContext).photo_100;
        }
        if (TextUtils.isEmpty(urlPhoto)) {
            this.mOwnerImage.setImageResource(R.drawable.default_profile);
        } else {
            Picasso.with(this.mContext).load(urlPhoto).tag(RecycleViewPicassoLight.PICASSO_TAG).placeholder((int) R.drawable.default_profile).error((int) R.drawable.default_profile).transform(new CircleTransform()).into(this.mOwnerImage);
        }
    }


    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= 21) {
            ((MainActivity) this.mContext).nextFragment(VideoDetailFragment.builder().mVideo(this.mVideo).build(),
                    ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity) this.mContext, this.mImageVideo, this.mContext.getString(R.string.translation_image)).toBundle());
            return;
        }
        ((MainActivity) this.mContext).nextFragment(VideoDetailFragment.builder().mVideo(this.mVideo).build());
    }
}
