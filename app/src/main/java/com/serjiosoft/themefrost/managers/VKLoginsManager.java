package com.serjiosoft.themefrost.managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKScopes;

/**
 * Created by autoexec on 27.02.2017.
 */

public final class VKLoginsManager {

    public static final String ACCESS_TOKEN_CACHE_KEY = "just_vk_video_cache_access_key";
    private static final String[] SCOPES = new String[]{VKScopes.FRIENDS, VKAttachments.TYPE_VIDEO, VKAttachments.TYPE_POST};
    private final Activity mActivity;
    private final VKCallback<VKAccessToken> mVKCallback;


    public VKLoginsManager(Activity mActivity, VKCallback<VKAccessToken> vkCallback) {
        this.mActivity = mActivity;
        this.mVKCallback = vkCallback;
    }


    public void login() {
        VKSdk.login(this.mActivity, SCOPES);
    }

    public static void logout(Context context) {
        VKAccessToken.removeTokenAtKey(context.getApplicationContext(), ACCESS_TOKEN_CACHE_KEY);
        VKSdk.logout();
    }


    public static boolean isLoggedIn(Context context) {
        return VKAccessToken.tokenFromSharedPreferences(context, ACCESS_TOKEN_CACHE_KEY) != null;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data){
        return VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                res.saveTokenToSharedPreferences(VKLoginsManager.this.mActivity.getApplicationContext(), VKLoginsManager.ACCESS_TOKEN_CACHE_KEY);
                VKLoginsManager.this.mVKCallback.onResult(res);
            }

            @Override
            public void onError(VKError error) {
                VKLoginsManager.this.mVKCallback.onError(error);
            }
        });
    }
}
