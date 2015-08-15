package io.gank.fragment.type;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.gank.R;
import io.gank.WebViewActivity;
import io.gank.adapter.TypeListAdapter;
import io.gank.http.GankHttpClient;
import io.gank.model.GankModel;
import io.gank.model.ResultModel;
import io.gank.view.UpRefreshListView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class VideoFragment extends Fragment implements UpRefreshListView.UpRefreshListener, SwipeRefreshLayout.OnRefreshListener {

    private static VideoFragment mNewFragment;
    private Context mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private UpRefreshListView mUpRefreshListView;
    private List<GankModel> mGankModels;
    private TypeListAdapter mAdapter;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_layout, container, false);
        mContext = getActivity();
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initListener() {

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mUpRefreshListView.setUpRefreshListener(this);
        mUpRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WebViewActivity.launch(mContext, mGankModels.get(position));
            }
        });
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

    private void getData(final int page) {
        GankHttpClient.getTypeData(getResources().getStringArray(R.array.type)[3], page, new Callback<ResultModel>() {
            @Override
            public void success(ResultModel resultModel, Response response) {
                Logger.e(resultModel.getResults().size() + "------");
                if (page == 1) {
                    mGankModels.clear();
                }
                mGankModels.addAll(resultModel.getResults());
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
                mUpRefreshListView.onRefreshFinish();
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.e(error.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);
                mUpRefreshListView.onRefreshFinish();
            }
        });
    }

    private void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_view);
        mUpRefreshListView = (UpRefreshListView) view.findViewById(R.id.rv_list);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mGankModels = new ArrayList<GankModel>();
        mAdapter = new TypeListAdapter(mContext, mGankModels);
        mUpRefreshListView.setAdapter(mAdapter);
    }


    @Override
    public void onRefresh() {
        page = 1;
        getData(page);
    }

    @Override
    public void onUpRefresh() {
        page++;
        getData(page);
    }

    public static VideoFragment newInstance() {
        if (mNewFragment == null) {
            mNewFragment = new VideoFragment();
        }
        return mNewFragment;
    }

}
