package io.gank.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.gank.GankApplication;
import io.gank.R;
import io.gank.activity.ImageGalleryActivity;
import io.gank.adapter.NewListAdapter;
import io.gank.http.GankHttpClient;
import io.gank.model.GankModel;
import io.gank.model.NewResultModel;
import io.gank.view.MyListView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private static NewFragment mNewFragment;
    private Context mContext;
    private ImageView mImageView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyListView mListView;
    private List<GankModel> mGankModels;
    private List<GankModel> mWelfare;
    private NewListAdapter mAdapter;
    private TextView tvNoData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        mContext = getActivity();
        initView(view);
        initListener();
        initData();
        return view;
    }


    private void initData() {

        mGankModels = new ArrayList<GankModel>();
        mWelfare = new ArrayList<GankModel>();

        mAdapter = new NewListAdapter(mContext, mGankModels);
        mListView.setAdapter(mAdapter);

        NewResultModel newResultModels = GankApplication.getOldGank(mContext);
        setData(newResultModels);
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getData();
            }
        }, 300);
    }

    private void initListener() {
        mImageView.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getData() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        GankHttpClient.getNewData(year, month + 1, day, new Callback<NewResultModel>() {
            @Override
            public void success(NewResultModel newResultModel, Response response) {
                setData(newResultModel);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.e(error.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);
            }

        });

    }


    private void setData(NewResultModel newResultModel) {
        if (!newResultModel.getResults().getWelfare().isEmpty()) {
            mGankModels.clear();
            mWelfare.clear();
            mGankModels.addAll(newResultModel.getResults().getAndroid());
            mGankModels.addAll(newResultModel.getResults().getIos());
            mGankModels.addAll(newResultModel.getResults().getExpand());
            mGankModels.addAll(newResultModel.getResults().getRecommendation());
            mGankModels.addAll(newResultModel.getResults().getVideo());
            mWelfare.addAll(newResultModel.getResults().getWelfare());
            String url = mWelfare.get(0).getUrl();
            Glide.with(mContext).load(url).centerCrop().into(mImageView);
            GankApplication.setOldGank(mContext, JSON.toJSONString(newResultModel));
            mAdapter.notifyDataSetChanged();
        } else {
            if (GankApplication.getOldGank(mContext).getResults().getWelfare().isEmpty()) {
                GankModel gankModel = new GankModel();
                String url = "http://wenjue.github.io/image/gank_image.jpg";
                gankModel.setUrl(url);
                mWelfare.add(gankModel);
                Glide.with(mContext).load(url).placeholder(R.color.stay_color).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(mImageView);
            }
        }
        if (mGankModels.isEmpty()) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }

    }


    private void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_view);
        mListView = (MyListView) view.findViewById(R.id.lv_list);
        mImageView = (ImageView) view.findViewById(R.id.sdv_view);
        tvNoData = (TextView) view.findViewById(R.id.tv_no_data);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.blue, R.color.yellow, R.color.green);


    }

    @Override
    public void onResume() {
        super.onResume();
        //防止重新进入时listview定位到最底部
        mListView.setFocusable(false);

    }

    public static NewFragment newInstance() {
        if (mNewFragment == null) {
            mNewFragment = new NewFragment();
        }
        return mNewFragment;
    }

    @Override
    public void onRefresh() {
        getData();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sdv_view:
                ImageGalleryActivity.launch(mContext, mWelfare, 0);
                break;
        }
    }
}
