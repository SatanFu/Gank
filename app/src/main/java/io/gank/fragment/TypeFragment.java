package io.gank.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.gank.R;
import io.gank.adapter.MainPageAdapter;
import io.gank.fragment.type.AndroidFragment;
import io.gank.fragment.type.ExpandFragment;
import io.gank.fragment.type.FrontEndFragment;
import io.gank.fragment.type.IosFragment;
import io.gank.fragment.type.RecommendationFragment;
import io.gank.fragment.type.VideoFragment;
import io.gank.fragment.type.WelfareFragment;


public class TypeFragment extends Fragment {

    private static TypeFragment mTypeFragment;
    private MainPageAdapter mPageAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Context mContext;

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
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        setupViewPager();
    }

    private void setupViewPager() {
        mPageAdapter = new MainPageAdapter(getChildFragmentManager(), getFragmentList(), getResources().getStringArray(R.array.type));
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private List<Fragment> getFragmentList() {
        List<Fragment> pagerFragmentList = new ArrayList<Fragment>();
        pagerFragmentList.add(WelfareFragment.newInstance());
        pagerFragmentList.add(AndroidFragment.newInstance());
        pagerFragmentList.add(IosFragment.newInstance());
        pagerFragmentList.add(VideoFragment.newInstance());
        pagerFragmentList.add(ExpandFragment.newInstance());
        pagerFragmentList.add(FrontEndFragment.newInstance());
        pagerFragmentList.add(RecommendationFragment.newInstance());
        return pagerFragmentList;

    }

}
