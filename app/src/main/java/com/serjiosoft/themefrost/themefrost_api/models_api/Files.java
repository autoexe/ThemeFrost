package com.serjiosoft.themefrost.themefrost_api.models_api;

import java.io.Serializable;

/**
 * Created by autoexec on 25.02.2017.
 */

public final class Files implements Serializable {

    public String external;
    public String mp4_1080;
    public String mp4_240;
    public String mp4_360;
    public String mp4_480;
    public String mp4_720;


    public boolean isExternalLink() {
        return this.external != null;
    }

    public boolean isMP4Video() {
        return (this.mp4_720 == null && this.mp4_480 == null && this.mp4_360 == null && this.mp4_240 == null && this.mp4_1080 == null) ? false : true;
    }


    public String getQuality(int quality) {
        switch (quality) {
            case 240:
                return this.mp4_240;
            case 360:
                return this.mp4_360;
            case 480:
                return this.mp4_480;
            case 720:
                return this.mp4_720;
            case 1080:
                return this.mp4_1080;
            default:
                return null;
        }
    }
}
