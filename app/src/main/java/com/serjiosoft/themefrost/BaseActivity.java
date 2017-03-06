package com.serjiosoft.themefrost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.serjiosoft.themefrost.fragments.base_classes.BaseFragment;

import java.io.Serializable;

/**
 * Created by autoexec on 22.02.2017.
 */

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.ThemeFrost_RED);
        super.onCreate(savedInstanceState);
    }

    public void replaceAllFragmentsIgnoreStateMain(BaseFragment baseFragment) {
        replaceAllFragments(true, 0, baseFragment);
    }

    protected void replaceAllFragments(boolean isMainActivity, int menuItemId, BaseFragment fragment){
        if (isMainActivity){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commitAllowingStateLoss();
        } else {
            MainActivity.intent(this).mIsMainActivity(true).mBaseFragmentClass(fragment.getClass()).mBaseFragmentArguments(fragment.getArguments()).start();
        }
    }

    public void nextFragment(BaseFragment fragment){
        MainActivity.intent(this).mIsMainActivity(false).mBaseFragmentClass(fragment.getClass()).mBaseFragmentArguments(fragment.getArguments()).start();
    }

    public void nextFragment(BaseFragment fragment, Bundle options){
        MainActivity.intent(this).mIsMainActivity(false).mBaseFragmentClass(fragment.getClass()).mBaseFragmentArguments(fragment.getArguments()).withOptions(options).start();
    }

}
