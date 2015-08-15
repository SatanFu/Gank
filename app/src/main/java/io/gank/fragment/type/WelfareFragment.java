package io.gank.fragment.type;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class WelfareFragment extends Fragment {

    private static WelfareFragment mWelfareFragment;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private List<GankModel> mGankModels;
    private WelfareAdapter mWelfareAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welfare, container, false);
        mContext = getActivity();
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        GankHttpClient.getTypeData(getResources().getStringArray(R.array.type)[0], 1, new Callback<ResultModel>() {
            @Override
            public void success(ResultModel resultModel, Response response) {
                mGankModels.clear();
                mGankModels.addAll(resultModel.getResults());
                mWelfareAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mGankModels = new ArrayList<GankModel>();
        mWelfareAdapter = new WelfareAdapter(mContext, mGankModels);
        mRecyclerView.setAdapter(mWelfareAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Logger.e("newState : " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                Logger.e("dx : " + dx + " dy : " + dy);
            }
        });


    }


    public static WelfareFragment newInstance() {
        if (mWelfareFragment == null) {
            mWelfareFragment = new WelfareFragment();
        }
        return mWelfareFragment;
    }

}
