package com.serjiosoft.themefrost.themefrost_api.request;

import com.google.gson.JsonSyntaxException;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;

/**
 * Created by autoexec on 28.02.2017.
 */

public class JustVKAction extends AsynchronousTask{

    @Override
    void gotActionInBackground(VKRequestType vKRequestType, VKResponse vKResponse) throws JsonSyntaxException, JSONException {
        switch (vKRequestType){
            case USERS_GET:{
                successResult(FilterVideoResponses.parseWithGson(vKResponse.json.getJSONArray("response").toString(), FilterVideoResponses.typeTokenUserArray));
                break;
            }
        }
    }

    protected JustVKAction(IVKRequest mVkRequestListener, VKResponse response, VKError error) {
        super(mVkRequestListener, response, error);
    }

}
