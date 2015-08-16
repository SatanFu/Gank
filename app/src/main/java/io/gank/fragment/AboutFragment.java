package io.gank.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.gank.R;
import io.gank.activity.WebViewActivity;
import io.gank.model.GankModel;


public class AboutFragment extends Fragment implements View.OnClickListener {

    private static AboutFragment mAboutFragment;
    private Context mContext;
    private TextView tvVersion;
    private TextView tvDaiMaJia;
    private TextView tvGank;
    private TextView tvDrakeet;
    private TextView tvMeiZi;
    private TextView tvMe;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        mContext = getActivity();
        initView(view);
        initListener();
        initData();
        return view;
    }


    private void initView(View view) {
        tvVersion = (TextView) view.findViewById(R.id.tv_version);
        tvDaiMaJia = (TextView) view.findViewById(R.id.tv_daimajia);
        tvGank = (TextView) view.findViewById(R.id.tv_gank);
        tvDrakeet = (TextView) view.findViewById(R.id.tv_drakeet);
        tvMeiZi = (TextView) view.findViewById(R.id.tv_meizi);
        tvMe = (TextView) view.findViewById(R.id.tv_satan);
    }

    private void initData() {
        try {
            String version = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            tvVersion.setText("Version " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        tvDaiMaJia.setOnClickListener(this);
        tvGank.setOnClickListener(this);
        tvDrakeet.setOnClickListener(this);
        tvMeiZi.setOnClickListener(this);
        tvMe.setOnClickListener(this);

    }

    public static AboutFragment newInstance() {
        if (mAboutFragment == null) {
            mAboutFragment = new AboutFragment();
        }
        return mAboutFragment;
    }


    @Override
    public void onClick(View v) {

        GankModel gankModel = new GankModel();

        switch (v.getId()) {
            case R.id.tv_daimajia:
                gankModel.setDesc(mContext.getResources().getString(R.string.daimajia));
                gankModel.setUrl("https://github.com/daimajia");
                break;
            case R.id.tv_gank:
                gankModel.setDesc(mContext.getResources().getString(R.string.gank));
                gankModel.setUrl("http://gank.io");
                break;
            case R.id.tv_drakeet:
                gankModel.setDesc(mContext.getResources().getString(R.string.drakeet));
                gankModel.setUrl("https://github.com/drakeet");
                break;
            case R.id.tv_meizi:
                gankModel.setDesc(mContext.getResources().getString(R.string.meizi));
                gankModel.setUrl("https://github.com/drakeet/Meizhi");
                break;
            case R.id.tv_satan:
                gankModel.setDesc("satan");
                gankModel.setUrl(mContext.getResources().getString(R.string.github));
                break;

        }
        WebViewActivity.launch(mContext, gankModel);
    }
}
