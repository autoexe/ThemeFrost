package com.serjiosoft.themefrost.fragments.base_classes;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.serjiosoft.themefrost.MainActivity;
import com.serjiosoft.themefrost.managers.JustLog;

/**
 * Created by autoexec on 24.02.2017.
 */

public abstract class BaseFragment extends Fragment implements IBaseEventer {

    protected MainActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity.registerBaseEventer(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity.unregisterBaseEventer();
    }

    @Override
    public void sendMessage(Object value){
        JustLog.d("TAG", "Receive message: " + value);
    }

    protected void initializeToolbar(Toolbar toolbar) {
        mActivity.initToolbar(toolbar);
    }

    protected boolean ignoreResponseIfNeeded() {
        return !isAdded();
    }

    protected void initializeMenuResource(int menuResource) {
        mActivity.setMenuResource(menuResource);
    }
}
