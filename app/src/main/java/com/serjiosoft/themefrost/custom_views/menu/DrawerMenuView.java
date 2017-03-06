package com.serjiosoft.themefrost.custom_views.menu;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.serjiosoft.themefrost.FrostLoaderApp;
import com.serjiosoft.themefrost.MainActivity;
import com.serjiosoft.themefrost.R;

import com.serjiosoft.themefrost.custom_views.MenuItemView;
import com.serjiosoft.themefrost.fragments.all_videos.AllVideosFragment;
import com.serjiosoft.themefrost.fragments.base_classes.BaseFragment;
import com.serjiosoft.themefrost.fragments.base_classes.BaseFragmentExtra;
import com.serjiosoft.themefrost.fragments.catalog.CatalogFragment;
import com.serjiosoft.themefrost.managers.UserController;
import com.serjiosoft.themefrost.managers.picasso.CircleTransform;
import com.serjiosoft.themefrost.themefrost_api.models_api.User;
import com.serjiosoft.themefrost.themefrost_api.request.VKRequestType;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.methods.VKApiWall;
import com.vk.sdk.api.model.VKAttachments;

/**
 * Created by autoexec on 22.02.2017.
 */

public class DrawerMenuView extends FrameLayout {

    private MainActivity mMainActivity;
    private AllVideosFragment mAllVideosFragment;
    private TextView mUserNameView;
    private TextView mUserIdLinkView;
    private ImageView mUserPhoto;
    private MenuItemView mSelectedItemView;
    private View mOffAds;

    private static final int DEFAULT_OPEN_SCREEN = (R.id.mivMyVideos_NM) ;





    public DrawerMenuView(@NonNull Context context) {
        super(context);
    }

    public DrawerMenuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.nav_menu, this);

        mUserNameView = (TextView) findViewById(R.id.tvUserName_NM);
        mUserIdLinkView= (TextView) findViewById(R.id.tvUserLinkId_NM);
        mUserPhoto = (ImageView) findViewById(R.id.ivIconUser_NM);
        mOffAds = findViewById(R.id.mivOffAds_NM);


        if (this.mOffAds != null) {
            this.mOffAds.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                   // onClickIntentMenuItem(view);
                }
            });
        }
        View view = findViewById(R.id.mivSettings_NM);
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                   // onClickIntentMenuItem(view);
                }
            });
        }
        view = findViewById(R.id.mivLogout_NM);
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    //onClickIntentMenuItem(view);
                }
            });
        }
        view = findViewById(R.id.mivMyVideos_NM);
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onClickMenuItem(view);
                }
            });
        }
        view = findViewById(R.id.mivAlbums_NM);
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onClickMenuItem(view);
                }
            });
        }
        view = findViewById(R.id.mivWall_NM);
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onClickMenuItem(view);
                }
            });
        }
        view = findViewById(R.id.mivCatalog_NM);
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onClickMenuItem(view);
                }
            });
        }
        view = findViewById(R.id.mivNews_NM);
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onClickMenuItem(view);
                }
            });
        }
        view = findViewById(R.id.mivFave_NM);
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onClickMenuItem(view);
                }
            });
        }
        view = findViewById(R.id.mivFriends_NM);
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onClickMenuItem(view);
                }
            });
        }
        view = findViewById(R.id.mivComunities_NM);
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onClickMenuItem(view);
                }
            });
        }
        view = findViewById(R.id.mivSearch_NM);
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onClickMenuItem(view);
                }
            });
        }
       // checkAdsAvailable();
    }



    public DrawerMenuView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void attachActivity(MainActivity mainActivity, int menuItemId, BaseFragmentExtra baseFragment){
        this.mMainActivity = mainActivity;
        injectUser(UserController.getUser(this.mMainActivity));
        BaseFragment startFragment = baseFragment.getBaseFragment();
        if (startFragment == null || menuItemId > 0){
            if (menuItemId <= 0){
                menuItemId = DEFAULT_OPEN_SCREEN;
            }
            onClickMenuItem(findViewById(menuItemId));
            return;
        }
        mMainActivity.replaceAllFragmentsIgnoreStateMain(startFragment);
    }


    protected void onClickMenuItem(View clickedView){
        if (mSelectedItemView != null){
            if (this.mSelectedItemView.getId() == clickedView.getId()){
                mMainActivity.closeDrawerMenu();
                return;
            }
            mSelectedItemView.setState(false);
        }

        switch (clickedView.getId()){

            case DEFAULT_OPEN_SCREEN:
                mMainActivity.replaceAllFragments(clickedView.getId(), AllVideosFragment.builder().build());
                break;

            case R.id.mivNews_NM:
                mMainActivity.replaceAllFragments(clickedView.getId(), CatalogFragment.builder()
                        .title(this.mMainActivity.getString(R.string.news))
                        .mTypeRequest(VKRequestType.NEWS_FEED_GET)
                        .mVKParameter(VKParameters.from(VKApiConst.FILTERS, VKAttachments.TYPE_VIDEO, VKApiWall.EXTENDED, Integer.valueOf(1)))
                        .build());
                break;
        }
        this.mSelectedItemView = (MenuItemView) clickedView;
        this.mSelectedItemView.setState(true);
       // InterstitialManager.getInstance(this.mMainActivity).requestNewInterstitial(this.mMainActivity);
       // ((FrostLoaderApp) this.mMainActivity.getApplicationContext()).getTracker().send(((ScreenViewBuilder) new ScreenViewBuilder().set("menu", this.mSelectedItemView.getTitle())).build());

    }



    private void injectUser(User user) {
        if (user == null) {
            this.mMainActivity.finish();
            return;
        }
        mUserNameView.setText(user.fullName());
        mUserIdLinkView.setText(mMainActivity.getString(R.string.link_user_vk, new Object[]{Integer.valueOf(user.id)}));
        Picasso.with(mMainActivity).load(user.photo_100).placeholder((int) R.drawable.ic_user).error((int) R.drawable.ic_user).transform(new CircleTransform()).into(mUserPhoto);

    }
}
