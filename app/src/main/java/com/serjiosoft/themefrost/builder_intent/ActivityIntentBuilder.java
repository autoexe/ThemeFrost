package com.serjiosoft.themefrost.builder_intent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by autoexec on 28.02.2017.
 */

public abstract class ActivityIntentBuilder<I extends ActivityIntentBuilder<I>> extends IntentBuilder<I> implements ActivityStarter  {

   /* protected Bundle lastOptions;

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
    }*/

    protected Bundle lastOptions;

    /**
     * Creates a builder for a given {@link android.app.Activity Activity}
     * class.
     *
     * @param context
     *            A {@link Context} of the application package implementing this
     *            class.
     * @param clazz
     *            The component class that is to be used for the {@link Intent}.
     */
    public ActivityIntentBuilder(Context context, Class<?> clazz) {
        super(context, clazz);
    }

    /**
     * Creates a builder which will append to a previously created
     * {@link android.content.Intent Intent}.
     *
     * @param context
     *            A {@link Context} of the application package implementing this
     *            class.
     * @param intent
     *            The previously created {@link Intent} to append to.
     *
     */
    public ActivityIntentBuilder(Context context, Intent intent) {
        super(context, intent);
    }

    @Override
    public final PostActivityStarter start() {
        startForResult(-1);
        return new PostActivityStarter(context);
    }

    @Override
    public abstract PostActivityStarter startForResult(int requestCode);

    /**
     * Adds additional options {@link Bundle} to the start method.
     *
     * @param options
     *            the {@link android.app.Activity Activity} options
     * @return an {@link ActivityStarter} instance to provide starter methods
     */
    public ActivityStarter withOptions(Bundle options) {
        lastOptions = options;
        return this;
    }

}
