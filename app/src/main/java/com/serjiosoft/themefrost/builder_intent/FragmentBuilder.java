package com.serjiosoft.themefrost.builder_intent;

import android.os.Bundle;

/**
 * Created by autoexec on 01.03.2017.
 */

public abstract class FragmentBuilder<I extends FragmentBuilder<I, F>, F> extends Builder {

    protected Bundle args = new Bundle();

    public abstract F build();




    public Bundle args() {
        return this.args;
    }

}
