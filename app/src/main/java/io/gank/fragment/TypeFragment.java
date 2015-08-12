package io.gank.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.gank.R;
import io.gank.adapter.MainPageAdapter;


public class TypeFragment extends Fragment {

    private static TypeFragment mTypeFragment;
    private MainPageAdapter mPageAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String[] mTypes = {"福利", "Android", "iOS", "休息视频", "拓展资源", "前端"};
    private Context mContext;
    private Toolbar mToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        mContext = getActivity();
        initView(view);
        return view;
    }

    public static TypeFragment newInstance() {
        if (mTypeFragment == null) {
            mTypeFragment = new TypeFragment();
        }
        return mTypeFragment;
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        setupViewPager();
    }


    private void setupViewPager() {
        mPageAdapter = new MainPageAdapter(getChildFragmentManager(), getFragmentList(), mTypes);
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    private List<Fragment> getFragmentList() {

        List<Fragment> pagerFragmentList = new ArrayList<Fragment>();
        for (String type : mTypes) {
            pagerFragmentList.add(new Fragment());
        }
//        pagerFragmentList.add(ChatFragment.newInstance());
//        pagerFragmentList.add(ContactFragment.newInstance());
        return pagerFragmentList;

    }

}
