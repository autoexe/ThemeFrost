package com.serjiosoft.themefrost.themefrost_api.request;

import android.os.AsyncTask;

import com.google.gson.JsonSyntaxException;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;

/**
 * Created by autoexec on 28.02.2017.
 */

public abstract class AsynchronousTask extends AsyncTask<VKRequestType, Void, Boolean> {

    private final VKError error;
    private int errorCode;
    private String errorMessage;
    private final IVKRequest mVkRequestListener;
    private final VKResponse response;
    private Object successObject;
    private String successParameter;

    abstract void gotActionInBackground(VKRequestType vKRequestType, VKResponse vKResponse) throws JsonSyntaxException, JSONException;

    protected AsynchronousTask(IVKRequest mVkRequestListener, VKResponse response, VKError error) {
        this.mVkRequestListener = mVkRequestListener;
        this.response = response;
        this.error = error;
    }

    protected Boolean doInBackground(VKRequestType... params) {
        if (this.response == null) {
            if (this.error != null) {
                this.errorCode = this.error.errorCode;
                this.errorMessage = this.error.errorMessage;
            } else {
                this.errorCode = 0;
                this.errorMessage = null;
            }
            return Boolean.valueOf(false);
        }
        try {
            gotActionInBackground(params[0], this.response);
            return Boolean.valueOf(true);
        } catch (JSONException e) {
            e.printStackTrace();
            this.errorCode = -1;
            this.errorMessage = null;
            return Boolean.valueOf(false);
        }
    }

    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if (success.booleanValue()) {
            this.mVkRequestListener.onSuccess(this.successObject, this.successParameter);
        } else {
            this.mVkRequestListener.onFailure(this.errorCode, this.errorMessage);
        }
    }

    protected void successResult(Object resultObject) {
        this.successObject = resultObject;
    }

    protected void successParameter(String resultParameter) {
        this.successParameter = resultParameter;
    }

}
