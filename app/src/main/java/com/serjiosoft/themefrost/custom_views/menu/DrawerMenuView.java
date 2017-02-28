package com.serjiosoft.themefrost.custom_views.menu;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.serjiosoft.themefrost.MainActivity;
import com.serjiosoft.themefrost.R;

import com.serjiosoft.themefrost.fragments.all_videos.AllVideosFragment;
import com.serjiosoft.themefrost.fragments.base_classes.BaseFragment;
import com.serjiosoft.themefrost.fragments.base_classes.BaseFragmentExtra;
import com.serjiosoft.themefrost.managers.UserController;
import com.serjiosoft.themefrost.managers.picasso.CircleTransform;
import com.serjiosoft.themefrost.themefrost_api.models_api.User;
import com.squareup.picasso.Picasso;

/**
 * Created by autoexec on 22.02.2017.
 */

public class DrawerMenuView extends FrameLayout {

    private MainActivity mMainActivity;
    private AllVideosFragment mAllVideosFragment;
    private TextView mUserNameView;
    private TextView mUserIdLinkView;
    private ImageView mUserPhoto;




    public DrawerMenuView(@NonNull Context context) {
        super(context);
    }

    public DrawerMenuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.nav_menu, this);

        mUserNameView = (TextView) findViewById(R.id.tvUserName_NM);
        mUserIdLinkView= (TextView) findViewById(R.id.tvUserLinkId_NM);
        mUserPhoto = (ImageView) findViewById(R.id.ivIconUser_NM);
    }

    public DrawerMenuView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void attachActivity(MainActivity mainActivity, int menuItemId, BaseFragmentExtra baseFragment){
        mMainActivity = mainActivity;
        injectUser(UserController.getUser(this.mMainActivity));
        BaseFragment startFragment = baseFragment.getBaseFragment();
        if (startFragment == null || menuItemId > 0){
            mMainActivity.replaceAllFragments(0, new AllVideosFragment());
            return;
        }
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
