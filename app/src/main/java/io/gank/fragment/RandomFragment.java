package io.gank.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.gank.R;
import io.gank.adapter.RandomAdapter;
import io.gank.http.GankHttpClient;
import io.gank.model.GankModel;
import io.gank.model.ResultModel;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RandomFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static RandomFragment mRandomFragment;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private List<GankModel> mGankModels;
    private RandomAdapter mRandomAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_random, container, false);
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
                getData();
            }
        }, 300);


    }


    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.blue, R.color.yellow, R.color.green);
        mGankModels = new ArrayList<GankModel>();
        mRandomAdapter = new RandomAdapter(mContext, mGankModels, null);
        mRecyclerView.setAdapter(mRandomAdapter);
    }

    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public static RandomFragment newInstance() {
        if (mRandomFragment == null) {
            mRandomFragment = new RandomFragment();
        }
        return mRandomFragment;
    }

    private void getData() {
        Random random = new Random();
        int index = random.nextInt(7);
        final String type = getResources().getStringArray(R.array.type)[index];
        GankHttpClient.getRandomData(type, new Callback<ResultModel>() {
            @Override
            public void success(ResultModel resultModel, Response response) {
                mGankModels.clear();
                mGankModels.addAll(resultModel.getResults());
                mRandomAdapter.setType(type);
                mRandomAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
