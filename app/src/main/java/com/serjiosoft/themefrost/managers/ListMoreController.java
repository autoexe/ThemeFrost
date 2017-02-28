package com.serjiosoft.themefrost.managers;

import com.serjiosoft.themefrost.BuildConfig;

/**
 * Created by autoexec on 25.02.2017.
 */

public final class ListMoreController {

    public static final int CATEGORY_SECTION_STEP = 16;
    public static int DEFAULT_OFFSET = 0;
    public static String DEFAULT_PARAMETER_OFFSET = null;
    public static final int VIDEOS_STEP = 20;
    public static final int WALL_STEP = 50;
    public static int DEFAULT_STEP = VIDEOS_STEP;
    private int count;
    private int offset;
    private String parameterOffset;


    public ListMoreController(int offset, int countStep) {
        this.offset = offset;
        this.count = countStep;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getCount() {
        return this.count;
    }

    public String getParameterOffset() {
        if (this.parameterOffset == null) {
            this.parameterOffset = BuildConfig.VERSION_NAME;
        }
        return this.parameterOffset;
    }

    public void setParameterOffset(String parameterOffset) {
        this.parameterOffset = parameterOffset;
    }

}
