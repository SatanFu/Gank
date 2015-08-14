package io.gank.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.gank.R;


public class FrontEndFragment extends Fragment {

    private static FrontEndFragment mFrontEndFragment;
//    private Toolbar mToolbar;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_random, container, false);
        mContext = getActivity();
        initView(view);
        return view;
    }

    private void initView(View view) {
//        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
    }

//    public Toolbar getToolbar() {
//        return mToolbar;
//    }


    public static FrontEndFragment newInstance() {
        if (mFrontEndFragment == null) {
            mFrontEndFragment = new FrontEndFragment();
        }
        return mFrontEndFragment;
    }

}
