package com.serjiosoft.themefrost.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.serjiosoft.themefrost.BaseActivity;
import com.serjiosoft.themefrost.MainActivity;
import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.managers.UserController;
import com.serjiosoft.themefrost.managers.VKLoginsManager;
import com.serjiosoft.themefrost.preferences.SharedPrefsController;
import com.serjiosoft.themefrost.themefrost_api.models_api.User;
import com.serjiosoft.themefrost.themefrost_api.request.IVKRequest;
import com.serjiosoft.themefrost.themefrost_api.request.JustVKRequest;
import com.serjiosoft.themefrost.themefrost_api.request.VKRequestType;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.model.VKApiUser;

import java.util.ArrayList;

/**
 * Created by autoexec on 22.02.2017.
 */

public class SplashActivity extends BaseActivity implements VKCallback<VKAccessToken> {

    protected RelativeLayout mContainerLogo;
    protected Button mButtonSynchronization;
    protected View mProgressView;
    private VKLoginsManager mLoginManager;

    private final int REQUEST_LOGIN = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContainerLogo = (RelativeLayout) findViewById(R.id.rlContainerLogo_AS);
        mButtonSynchronization = (Button) findViewById(R.id.btnSynchronization_AS);
        mProgressView = findViewById(R.id.pbProgress_AS);


        startMainAnimation();
        createManager();


        if (mButtonSynchronization!=null){
            mButtonSynchronization.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLoginManager.login();
                }
            });
        }



    }

    private final IVKRequest<ArrayList<User>> mUserGetResponse = new IVKRequest<ArrayList<User>>() {
        @Override
        public void onFailure(int i, String str) {
            if (!SplashActivity.this.isFinishing()){

            }
        }

        @Override
        public void onSuccess(ArrayList<User> result, @Nullable String parameter) {
            if (!SplashActivity.this.isFinishing()){
                if (result == null || result.size() <= 0){
                    SplashActivity.this.changeStateToLoading(false);
                    return;
                }
                UserController.storeUser((User) result.get(0));
                SharedPrefsController.saveToPref(SplashActivity.this, SharedPrefsController.KEY_USER, new Gson().toJson(UserController.getUser(SplashActivity.this)));
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }
    };

    protected void createManager(){
        mLoginManager = new VKLoginsManager(this, this);
        if (VKLoginsManager.isLoggedIn(getApplicationContext())){
            User user = (User) new Gson().fromJson(SharedPrefsController.getFromPref(this, SharedPrefsController.KEY_USER), User.class);
            if (user != null){
                UserController.storeUser(user);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }
    }

    protected void startMainAnimation() {
        changeStateToLoading(false);
        mContainerLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_start_animation));
    }

    private void changeStateToLoading(boolean loading) {
        if (loading) {
            mProgressView.setVisibility(View.VISIBLE);
            mButtonSynchronization.setText(R.string.loading);
            mButtonSynchronization.setEnabled(false);
            return;
        }
        mProgressView.setVisibility(View.GONE);
        mButtonSynchronization.setText(R.string.button);
        mButtonSynchronization.setEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!this.mLoginManager.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onResult(VKAccessToken res) {
        changeStateToLoading(true);
        new JustVKRequest(this.mUserGetResponse, VKRequestType.USERS_GET)
                .putVKParameter(VKApiConst.OWNER_ID, res.userId)
                .putVKParameter(VKApiConst.FIELDS, VKApiUser.FIELD_PHOTO_100)
                .execute();
    }

    @Override
    public void onError(VKError error) {

    }
}
