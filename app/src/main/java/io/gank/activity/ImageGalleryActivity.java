package io.gank.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.gank.R;
import io.gank.adapter.ViewPagerAdapter;
import io.gank.model.GankModel;

public class ImageGalleryActivity extends Activity implements
        OnClickListener {

    private ViewPager mViewPager;
    private TextView mCount;
    private Context mContext;
    private List<GankModel> mGankModels;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery_layout);
        mContext = this;
        initData();
        initView();
    }

    public static void launch(Context context, List<GankModel> gankModels, int position) {
        Intent intent = new Intent(context, ImageGalleryActivity.class);
        intent.putExtra("gank_models", (ArrayList<GankModel>) gankModels);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    private void initData() {
        mGankModels = (List<GankModel>) getIntent().getSerializableExtra("gank_models");
        position = getIntent().getIntExtra("position", 0);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mCount = (TextView) findViewById(R.id.count);
        mCount.setText((position + 1) + "/" + mGankModels.size());

        mViewPager.setAdapter(new ViewPagerAdapter(mContext, mGankModels));

        mViewPager.setCurrentItem(position);

        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCount.setText((position + 1) + "/" + mGankModels.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sdv_view:
                this.finish();
                break;
        }
    }

}
