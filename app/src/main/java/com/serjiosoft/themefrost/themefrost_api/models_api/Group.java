package com.serjiosoft.themefrost.themefrost_api.models_api;

import java.io.Serializable;

/**
 * Created by autoexec on 25.02.2017.
 */

public final class Group implements IOwnerSelf, Serializable {

    public int id;
    public int is_closed;
    public String name;
    public String photo_100;
    public String photo_50;
    public String screen_name;
    public String type;



    @Override
    public String getOwnerName() {
        return this.name;
    }

    @Override
    public String getOwnerUrlPhoto() {
        return this.photo_100;
    }
}
