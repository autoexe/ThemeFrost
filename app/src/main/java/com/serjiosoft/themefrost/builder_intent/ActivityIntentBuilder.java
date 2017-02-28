package com.serjiosoft.themefrost.builder_intent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by autoexec on 28.02.2017.
 */

public abstract class ActivityIntentBuilder<I extends ActivityIntentBuilder<I>> extends IntentBuilder<I> implements ActivityStarter  {

    protected Bundle lastOptions;

    public abstract void startForResult(int i);

    public ActivityIntentBuilder(Context context, Class<?> clazz) {
        super(context, clazz);
    }

    public ActivityIntentBuilder(Context context, Intent intent) {
        super(context, intent);
    }


    public final void start() {
        startForResult(-1);
    }

    public ActivityStarter withOptions(Bundle options) {
        lastOptions = options;
        return this;
    }

}
