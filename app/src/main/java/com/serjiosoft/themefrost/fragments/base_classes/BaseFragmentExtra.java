package com.serjiosoft.themefrost.fragments.base_classes;

import android.os.Bundle;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by autoexec on 24.02.2017.
 */

public class BaseFragmentExtra implements Serializable {

    public Bundle bundleForFragment;
    public Class classBaseFragment;

    public BaseFragmentExtra(Class classBaseFragment, Bundle bundleForFragment) {
        this.bundleForFragment = bundleForFragment;
        this.classBaseFragment = classBaseFragment;
    }

    public BaseFragment getBaseFragment(){
        if (classBaseFragment == null) {
            return null;
        }
        try {
            final BaseFragment baseFragment = (BaseFragment) classBaseFragment.getConstructor(new Class[0]).newInstance(new Object[0]);
            baseFragment.setArguments(bundleForFragment);
            return baseFragment;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
