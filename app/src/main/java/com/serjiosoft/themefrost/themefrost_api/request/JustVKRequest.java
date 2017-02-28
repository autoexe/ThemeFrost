package com.serjiosoft.themefrost.themefrost_api.request;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.Map;

/**
 * Created by autoexec on 28.02.2017.
 */

public final class JustVKRequest {

    private VKParameters mVkParameters;
    private final IVKRequest mVkRequestListener;
    private final VKRequestType mVkRequestType;

    public JustVKRequest(IVKRequest vkRequest, VKRequestType vkRequestType) {
        this.mVkRequestListener = vkRequest;
        this.mVkRequestType = vkRequestType;
        this.mVkParameters = new VKParameters();
    }

    private final VKRequest.VKRequestListener mLocalVKListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            super.onComplete(response);
            new JustVKAction(mVkRequestListener, response, null).execute(new VKRequestType[]{mVkRequestType});
        }

        @Override
        public void onError(VKError error) {
            super.onError(error);
        }
    };


    public JustVKRequest putVKParameter(String key, Object value) {
        this.mVkParameters.put(key, value);
        return this;
    }

    public JustVKRequest setVKParameter(VKParameters vkParameters) {
        this.mVkParameters = vkParameters;
        return this;
    }

    public JustVKRequest setVKParameter(Map<String, Object> vkParametersMap) {
        this.mVkParameters = new VKParameters(vkParametersMap);
        return this;
    }

    public void execute(){
        switch (mVkRequestType){

            case USERS_GET:{
                VKApi.users().get(mVkParameters).executeWithListener(mLocalVKListener);
                break;
            }

            case VIDEO_GET_CATALOG_SECTION:{
                break;
            }

            case WALL_GET:{
                break;
            }

            case VIDEO_GET:{
                VKApi.video().get(mVkParameters).executeWithListener(mLocalVKListener);
                break;
            }
        }
    }
}
