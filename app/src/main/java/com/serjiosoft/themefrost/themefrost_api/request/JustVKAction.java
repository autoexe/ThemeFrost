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

            case VIDEO_GET_USER_VIDEOS:
            case VIDEO_GET:{
                successResult(FilterVideoResponses.getVideos(vKResponse));
                break;
            }

            case NEWS_FEED_GET:{
                successResult(FilterVideoResponses.getVideosFromNewsResponse(vKResponse));
                successParameter(vKResponse.json.getJSONObject("response").getString("next_from"));
                break;
            }

            case VIDEO_GET_CATALOG:{
                successResult(FilterVideoResponses.getCatalogs(vKResponse));
                break;
            }

            case VIDEO_GET_CATALOG_SECTION:{
                successResult(FilterVideoResponses.getVideos(vKResponse));
                if (vKResponse.json.getJSONObject("response").has("next")) {
                    this.successParameter(vKResponse.json.getJSONObject("response").getString("next"));
                    break;
                }
                successParameter(null);
                break;
            }
        }
    }

    protected JustVKAction(IVKRequest mVkRequestListener, VKResponse response, VKError error) {
        super(mVkRequestListener, response, error);
    }

}
