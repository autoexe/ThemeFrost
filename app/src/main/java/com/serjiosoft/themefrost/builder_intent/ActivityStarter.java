package com.serjiosoft.themefrost.builder_intent;

/**
 * Created by autoexec on 01.03.2017.
 */

public interface ActivityStarter {

    PostActivityStarter start();

    PostActivityStarter startForResult(int requestCode);

}
