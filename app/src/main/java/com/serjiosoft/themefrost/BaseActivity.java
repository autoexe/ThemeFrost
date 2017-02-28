package com.serjiosoft.themefrost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.serjiosoft.themefrost.fragments.base_classes.BaseFragment;

/**
 * Created by autoexec on 22.02.2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.ThemeFrost_RED);
        super.onCreate(savedInstanceState);
    }

    public void replaceAllFragmentsIgnoreStateMain(BaseFragment baseFragment) {
        replaceAllFragments(true, 0, baseFragment);
    }

    protected void replaceAllFragments(boolean isMainActivity, int menuItemId, BaseFragment fragment){
        if (isMainActivity){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commitAllowingStateLoss();
        }
    }
}
