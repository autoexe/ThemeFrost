package com.serjiosoft.themefrost.fragments.all_videos;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by autoexec on 24.02.2017.
 */

public class VideosPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragments = new ArrayList();
    private ArrayList<String> mTitles = new ArrayList();


    public VideosPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title){
        mFragments.add(fragment);
        mTitles.add(title);

    }

    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
