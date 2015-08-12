package io.gank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class MainPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> mPageFragments;
    private String[] mTitles;

    public MainPageAdapter(FragmentManager fm, List<Fragment> pageFragments, String[] titles) {
        super(fm);

        this.mPageFragments = pageFragments;
        mTitles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int i) {
        return mPageFragments.get(i);
    }

    @Override
    public int getCount() {
        return mPageFragments.size();
    }

}
