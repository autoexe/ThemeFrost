package com.serjiosoft.themefrost.builder_intent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by autoexec on 28.02.2017.
 */

public abstract class IntentBuilder<I extends IntentBuilder<I>> extends Builder {


    protected final Context context;
    protected final Intent intent;


    public IntentBuilder(Context context, Class<?> clazz) {
        this(context, new Intent(context, clazz));
    }

    public IntentBuilder(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    public Context getContext() {
        return this.context;
    }

    public Intent get() {
        return this.intent;
    }


    public I extra(String name, Serializable value) {
        intent.putExtra(name, value);
        return (I) this;
    }

    public I extra(String name, boolean value) {
        this.intent.putExtra(name, value);
        return (I) this;
    }

    public I extra(String name, Bundle value) {
        this.intent.putExtra(name, value);
        return (I) this;
    }

}
