package com.serjiosoft.themefrost.themefrost_api.models_api;

import com.serjiosoft.themefrost.themefrost_api.models_api.IOwnerSelf;

import java.io.Serializable;

/**
 * Created by autoexec on 24.02.2017.
 */

public class User implements Serializable, IOwnerSelf {

    public String first_name;
    public int id;
    public String last_name;
    public String photo_100;


    public String fullName() {
        return this.first_name + " " + this.last_name;
    }

    public String getOwnerName() {
        return fullName();
    }

    public String getOwnerUrlPhoto() {
        return this.photo_100;
    }

}
