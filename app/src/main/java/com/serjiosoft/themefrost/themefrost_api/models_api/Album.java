package com.serjiosoft.themefrost.themefrost_api.models_api;

import com.serjiosoft.themefrost.BuildConfig;

/**
 * Created by autoexec on 25.02.2017.
 */

public final class Album implements CatalogKid, IOwnerSelf {

    public int count;
    public Group group;
    public int id;
    public int is_system;
    public int owner_id;
    public String photo_160;
    public String photo_320;
    public User profile;
    public String title;
    public long updated_time;



    @Override
    public String getOwnerName() {
        if (this.group != null) {
            return this.group.getOwnerName();
        }
        return this.profile != null ? this.profile.getOwnerName() : BuildConfig.VERSION_NAME;

    }

    @Override
    public String getOwnerUrlPhoto() {
        if (this.group != null) {
            return this.group.getOwnerUrlPhoto();
        }
        return this.profile != null ? this.profile.getOwnerUrlPhoto() : BuildConfig.VERSION_NAME;
    }

}

