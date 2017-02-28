package com.serjiosoft.themefrost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.serjiosoft.themefrost.builder_intent.ActivityIntentBuilder;
import com.serjiosoft.themefrost.builder_intent.IntentBuilder;
import com.serjiosoft.themefrost.custom_views.menu.DrawerMenuView;
import com.serjiosoft.themefrost.fragments.base_classes.BaseFragment;
import com.serjiosoft.themefrost.fragments.base_classes.BaseFragmentExtra;
import com.serjiosoft.themefrost.fragments.base_classes.IBaseEventer;

import java.io.Serializable;

public class MainActivity extends BaseActivity {

    private FrameLayout mContainerLayout;
    private DrawerLayout mDrawerLayout;
    private DrawerMenuView mDrawerMenu;
    private ActionBarDrawerToggle mDrawerToggle;

    private IBaseEventer mBaseEventer;
    private int choosedMenuItem;
    private Bundle mBaseFragmentArguments;
    private Class<? extends BaseFragment> mBaseFragmentClass;
    private boolean mIsMainActivity = true;
    private int menuResource = 0;

    public static final String M_IS_MAIN_ACTIVITY_EXTRA = "mIsMainActivity";
    public static final String M_BASE_FRAGMENT_CLASS_EXTRA = "mBaseFragmentClass";
    public static final String M_BASE_FRAGMENT_ARGUMENTS_EXTRA = "mBaseFragmentArguments";


    public static IntentBuilder_ intent(Context context) {
        return new IntentBuilder_(context);
    }

    public static IntentBuilder_ intent(android.app.Fragment fragment) {
        return new IntentBuilder_(fragment);
    }

    public static IntentBuilder_ intent(Fragment supportFragment) {
        return new IntentBuilder_(supportFragment);
    }



    public static class IntentBuilder_ extends ActivityIntentBuilder<IntentBuilder_> {

        private Fragment fragmentSupport;
        private android.app.Fragment fragment;


        public IntentBuilder_(Context context) {
            super(context, MainActivity.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), MainActivity.class);
            this.fragment = fragment;
        }

        public IntentBuilder_(Fragment fragment) {
            super(fragment.getActivity(), MainActivity.class);
            fragmentSupport = fragment;
        }


        public IntentBuilder_ mBaseFragmentArguments(Bundle mBaseFragmentArguments) {
            return (IntentBuilder_) super.extra(MainActivity.M_BASE_FRAGMENT_ARGUMENTS_EXTRA, mBaseFragmentArguments);
        }

        public IntentBuilder_ mIsMainActivity(boolean mIsMainActivity) {
            return (IntentBuilder_) super.extra(MainActivity.M_IS_MAIN_ACTIVITY_EXTRA, mIsMainActivity);
        }

        public IntentBuilder_ mBaseFragmentClass(Class<? extends BaseFragment> mBaseFragmentClass) {
            return (IntentBuilder_) super.extra(MainActivity.M_BASE_FRAGMENT_CLASS_EXTRA, (Serializable) mBaseFragmentClass);
        }

        @Override
        public void startForResult(int requestCode) {
            if (fragmentSupport != null) {
                fragmentSupport.startActivityForResult(intent, requestCode);
            } else if (fragment != null) {
                if (Build.VERSION.SDK_INT >= 16) {
                    fragment.startActivityForResult(intent, requestCode, lastOptions);
                } else {
                    fragment.startActivityForResult(intent, requestCode);
                }
            } else if (context instanceof Activity) {
                ActivityCompat.startActivityForResult((Activity) context, intent, requestCode, lastOptions);
            } else if (Build.VERSION.SDK_INT >= 16) {
                context.startActivity(intent, lastOptions);
            } else {
                context.startActivity(intent);
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContainerLayout = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerMenu = (DrawerMenuView) findViewById(R.id.dmvLeftMenu_AM);

        startConfigs();
    }

    private void startConfigs(){
        mDrawerMenu.attachActivity(this, choosedMenuItem, new BaseFragmentExtra(mBaseFragmentClass, mBaseFragmentArguments));
    }

    public void initToolbar(Toolbar toolbar){
        if (mIsMainActivity) {
            initDrawerWithHamburger(toolbar);
        } else {
            initDrawerWithBackAction(toolbar);
        }
    }

    private void initDrawerWithBackAction(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mDrawerToggle = null;
        }
    }



    private void initDrawerWithHamburger(Toolbar toolbar){
        setSupportActionBar(toolbar);
        if (mDrawerLayout != null){
            ActionBar mActionBar = getSupportActionBar();
            if (mActionBar != null){
                mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        MainActivity.this.mContainerLayout.setTranslationX((((float) (MainActivity.this.mDrawerMenu.getWidth() * 2)) * slideOffset) / 3.0f);
                        super.onDrawerSlide(drawerView, slideOffset);
                    }
                    public void onDrawerClosed(View view) {
                        super.onDrawerClosed(view);
                    }
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }
                };
                mDrawerLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.this.mDrawerToggle.syncState();
                    }
                });
                mDrawerLayout.setDrawerListener(this.mDrawerToggle);
                mActionBar.setDisplayHomeAsUpEnabled(true);
                mActionBar.setHomeButtonEnabled(true);
            }
        }
    }

    @Override
    public void replaceAllFragmentsIgnoreStateMain(BaseFragment baseFragment){
        menuResource = 0;
        closeDrawerMenu();
        super.replaceAllFragmentsIgnoreStateMain(baseFragment);
    }

    public void replaceAllFragments(int idMenuItem, BaseFragment fragment){
        menuResource = 0;
        closeDrawerMenu();
        super.replaceAllFragments(this.mIsMainActivity, idMenuItem, fragment);
    }

    public void closeDrawerMenu() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void registerBaseEventer(IBaseEventer iBaseEventer) {
        mBaseEventer = iBaseEventer;
    }

    public void unregisterBaseEventer() {
        mBaseEventer = null;
    }

    public void sendEvent(Object value) {
        if (this.mBaseEventer != null) {
            this.mBaseEventer.sendMessage(value);
        }
    }


    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
        injectExtras();
    }

    private void injectExtras() {
        Bundle extras = getIntent().getExtras();
    }
}
