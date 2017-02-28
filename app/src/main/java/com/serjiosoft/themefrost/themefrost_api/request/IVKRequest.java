package com.serjiosoft.themefrost.themefrost_api.request;

import android.support.annotation.Nullable;

/**
 * Created by autoexec on 25.02.2017.
 */

public interface IVKRequest<T> {

    void onFailure(int i, String str);
    void onSuccess(T t, @Nullable String str);

}
