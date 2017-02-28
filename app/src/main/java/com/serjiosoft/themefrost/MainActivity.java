package com.serjiosoft.themefrost;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.serjiosoft.themefrost.custom_views.menu.DrawerMenuView;
import com.serjiosoft.themefrost.fragments.base_classes.BaseFragment;
import com.serjiosoft.themefrost.fragments.base_classes.BaseFragmentExtra;
import com.serjiosoft.themefrost.fragments.base_classes.IBaseEventer;

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
        initDrawerWithHamburger(toolbar);
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
}
