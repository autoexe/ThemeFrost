package com.serjiosoft.themefrost.themefrost_api.models_api;

import com.serjiosoft.themefrost.BuildConfig;

import java.io.Serializable;

/**
 * Created by autoexec on 25.02.2017.
 */

public class Video implements IOwnerSelf, CatalogKid, Serializable {

    public long adding_date;
    public int chOwnerId;
    public int comments;
    public long date;
    public String description;
    public int duration;
    public Files files;
    public Group group;
    public int id;
    public int live;
    public int owner_id;
    public String photo_130;
    public String photo_320;
    public String photo_640;
    public String photo_800;
    public String player;
    public int processing;
    public User profile;
    public String title;
    public int views;


    public int getOwnerId() {
        if (this.chOwnerId == 0) {
            return this.owner_id;
        }
        return this.chOwnerId;
    }

    public String getMaxPreviewPhoto() {
        if (this.photo_640 != null) {
            return this.photo_640;
        }
        if (this.photo_320 != null) {
            return this.photo_320;
        }
        if (this.photo_130 != null) {
            return this.photo_130;
        }
        return null;
    }

    public long getOwnerDateAdded() {
        return this.adding_date == 0 ? this.date : this.adding_date;
    }


    @Override
    public String getOwnerName() {
        if (this.group != null) {
            return this.group.getOwnerName();
        }
        return this.profile != null ? this.profile.getOwnerName() : this.title;

    }

    @Override
    public String getOwnerUrlPhoto() {
        if (this.group != null) {
            return this.group.getOwnerUrlPhoto();
        }
        return this.profile != null ? this.profile.getOwnerUrlPhoto() : BuildConfig.VERSION_NAME;
    }
}
