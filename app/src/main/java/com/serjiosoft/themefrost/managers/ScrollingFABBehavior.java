package com.serjiosoft.themefrost.managers;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.serjiosoft.themefrost.R;

/**
 * Created by autoexec on 01.03.2017.
 */

public class ScrollingFABBehavior extends FloatingActionButton.Behavior {

    private int toolbarHeight;



    public ScrollingFABBehavior(Context context, AttributeSet attrs) {
        this.toolbarHeight = getToolbarHeight(context);
    }

    private int getToolbarHeight(Context context) {
        TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0.0f);
        styledAttributes.recycle();
        return toolbarHeight;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton fab, View dependency) {
        return super.layoutDependsOn(parent, fab, dependency) || (dependency instanceof AppBarLayout);
    }

    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton fab, View dependency) {
        boolean returnValue = super.onDependentViewChanged(parent, fab, dependency);
        if (dependency instanceof AppBarLayout) {
            float ratio = dependency.getY() / ((float) this.toolbarHeight);
            fab.setTranslationY(((float) (-(fab.getHeight() + ((CoordinatorLayout.LayoutParams) fab.getLayoutParams()).bottomMargin))) * ratio);
        }
        return returnValue;
    }

}
