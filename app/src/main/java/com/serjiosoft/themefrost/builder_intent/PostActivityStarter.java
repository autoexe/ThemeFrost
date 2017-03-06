package com.serjiosoft.themefrost.builder_intent;

import android.app.Activity;
import android.content.Context;

/**
 * Created by autoexec on 01.03.2017.
 */

public final class PostActivityStarter {

    private Context context;

    public PostActivityStarter(Context context) {
        this.context = context;
    }

    public void withAnimation(int enterAnim, int exitAnim) {
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }
}
