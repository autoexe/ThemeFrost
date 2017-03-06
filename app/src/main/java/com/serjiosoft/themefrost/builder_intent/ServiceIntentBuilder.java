package com.serjiosoft.themefrost.builder_intent;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Created by autoexec on 01.03.2017.
 */

public abstract class ServiceIntentBuilder<I extends ServiceIntentBuilder<I>> extends IntentBuilder<I> {

    public ServiceIntentBuilder(Context context, Class<?> clazz) {
        super(context, clazz);
    }

    public ServiceIntentBuilder(Context context, Intent intent) {
        super(context, intent);
    }

    public ComponentName start() {
        return context.startService(intent);
    }

    public boolean stop() {
        return context.stopService(intent);
    }
}
