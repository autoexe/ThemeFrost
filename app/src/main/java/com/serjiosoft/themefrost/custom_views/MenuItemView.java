package com.serjiosoft.themefrost.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.serjiosoft.themefrost.R;

/**
 * Created by autoexec on 24.02.2017.
 */

public class MenuItemView extends FrameLayout {

    private boolean isTablet = false;
    private int mBgColorItem;
    private int mColorItem;
    protected TintImageView mIconView;
    private boolean mIntentSupport = false;
    private Drawable mItemIcon;
    private String mItemTitle;
    private int mSelectBgColorItem;
    private int mSelectColorItem;
    protected TextView mTitleView;
    private TypedValue mTypedValue = new TypedValue();


    public MenuItemView(@NonNull Context context) {
        super(context);
        beforeConfigs(null, 0);
    }

    public MenuItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.item_menu_drawer, this);
        beforeConfigs(attrs, 0);
    }

    public MenuItemView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        beforeConfigs(attrs, defStyleAttr);
    }

    private void beforeConfigs(AttributeSet attrs, int defStyle){
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ItemMenuView, defStyle, 0);
            mItemIcon = a.getDrawable(R.styleable.ItemMenuView_iconMenu);
            mItemTitle = a.getString(R.styleable.ItemMenuView_iconTitle);
            mIntentSupport = a.getBoolean(R.styleable.ItemMenuView_justIntent, false);
            a.recycle();
        }
        getContext().getTheme().resolveAttribute(R.attr.colorActionBar, mTypedValue, true);
        mColorItem = mTypedValue.data;
        getContext().getTheme().resolveAttribute(R.attr.colorStatusBar, mTypedValue, true);
        mSelectColorItem = mTypedValue.data;
        getContext().getTheme().resolveAttribute(R.attr.colorBackgrounds, mTypedValue, true);
        mBgColorItem = mTypedValue.data;
        getContext().getTheme().resolveAttribute(R.attr.colorBackgroundsDarkness, mTypedValue, true);
        mSelectBgColorItem = mTypedValue.data;
        mTypedValue = null;

        mIconView = (TintImageView) findViewById(R.id.ivIconMenu_IMD);
        mTitleView = (TextView) findViewById(R.id.tvTitleMenu_IMD);

        initializeViews();
    }

    protected void initializeViews() {
        setState(false);
        setIcon(mItemIcon);
        setTitle(mItemTitle);
    }

    public void setIcon(Drawable icon) {
        mItemIcon = icon;
        mIconView.setImageDrawable(mItemIcon);
    }

    public void setTitle(String title) {
        mItemTitle = title;
        mTitleView.setText(mItemTitle);
    }

    public String getTitle() {
        return mItemTitle;
    }

    public void setState(boolean isSelected) {
        if (isTablet) {
            if (isSelected) {
                mIconView.setColorFilter(mColorItem);
                mTitleView.setTextColor(mColorItem);
                this.mTitleView.setTypeface(null, 1);
                setBackgroundColor(-1);
                return;
            }
            mIconView.setColorFilter(mBgColorItem);
            mTitleView.setTextColor(-1);
            mTitleView.setTypeface(null, 0);
            setBackgroundDrawable(null);
        } else if (mIntentSupport) {
            mTitleView.setTextColor(mBgColorItem);
            mIconView.setColorFilter(mBgColorItem);
        } else if (isSelected) {
            mIconView.setColorFilter(mSelectColorItem);
            mTitleView.setTypeface(null, 1);
            setBackgroundColor(mSelectBgColorItem);
        } else {
            mIconView.setColorFilter(mColorItem);
            mTitleView.setTypeface(null, 0);
            setBackgroundDrawable(null);
        }
    }

}
