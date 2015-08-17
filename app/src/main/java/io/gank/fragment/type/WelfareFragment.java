package io.gank.fragment.type;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.gank.R;
import io.gank.adapter.WelfareAdapter;
import io.gank.http.GankHttpClient;
import io.gank.model.GankModel;
import io.gank.model.ResultModel;
import io.gank.view.UpRefreshRecyclerView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class WelfareFragment extends Fragment implements UpRefreshRecyclerView.UpRefreshListener, SwipeRefreshLayout.OnRefreshListener {

    private static WelfareFragment mWelfareFragment;
    private Context mContext;
    private UpRefreshRecyclerView mUpRefreshRecyclerView;
    private List<GankModel> mGankModels;
    private WelfareAdapter mWelfareAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welfare, container, false);
        mContext = getActivity();
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                page = 1;
                getData(page);
            }
        }, 300);

    }

    private void initListener() {

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mUpRefreshRecyclerView.setUpRefreshListener(this);
    }

    private void getData(final int page) {
        GankHttpClient.getTypeData(getResources().getStringArray(R.array.type)[0], page, new Callback<ResultModel>() {
            @Override
            public void success(ResultModel resultModel, Response response) {
                if (page == 1) {
                    mGankModels.clear();
                }
                mGankModels.addAll(resultModel.getResults());
                mWelfareAdapter.notifyDataSetChanged();

                mUpRefreshRecyclerView.onRefreshFinish();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.e(error.getMessage());
                mUpRefreshRecyclerView.onRefreshFinish();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_view);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.blue, R.color.yellow, R.color.green);

        mUpRefreshRecyclerView = (UpRefreshRecyclerView) view.findViewById(R.id.rv_list);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mUpRefreshRecyclerView.setLayoutManager(layoutManager);

        mGankModels = new ArrayList<GankModel>();
        mWelfareAdapter = new WelfareAdapter(mContext, mGankModels);
        mUpRefreshRecyclerView.setAdapter(mWelfareAdapter);


    }


    public static WelfareFragment newInstance() {
        if (mWelfareFragment == null) {
            mWelfareFragment = new WelfareFragment();
        }
        return mWelfareFragment;
    }

    @Override
    public void onUpRefresh() {
        page++;
        getData(page);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData(page);
    }
}
