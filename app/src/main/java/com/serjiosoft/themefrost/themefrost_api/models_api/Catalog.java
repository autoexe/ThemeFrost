package com.serjiosoft.themefrost.themefrost_api.models_api;

import com.serjiosoft.themefrost.BuildConfig;

import java.util.ArrayList;

/**
 * Created by autoexec on 25.02.2017.
 */

public final class Catalog {

    public long date;
    public Group group;
    public String id;
    public ArrayList<CatalogKid> items;
    public String name;
    public String next;
    public User profile;
    public String type;


    public String getPhotoUrl() {
        if (this.group != null) {
            return this.group.getOwnerUrlPhoto();
        }
        if (this.profile != null) {
            return this.profile.getOwnerUrlPhoto();
        }
        return null;
    }

    public String getNameCatalog() {
        if (this.name != null) {
            return this.name;
        }
        if (this.group != null) {
            return this.group.getOwnerName();
        }
        if (this.profile != null) {
            return this.profile.getOwnerName();
        }
        return BuildConfig.VERSION_NAME;
    }

}
