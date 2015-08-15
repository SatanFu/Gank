package io.gank.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.gank.R;
import io.gank.adapter.NewListAdapter;
import io.gank.adapter.TypeListAdapter;
import io.gank.http.GankHttpClient;
import io.gank.model.GankModel;
import io.gank.model.NewResultModel;
import io.gank.util.ViewUtil;
import io.gank.view.MyListView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static NewFragment mNewFragment;
    private Context mContext;
    private SimpleDraweeView mSimpleDraweeView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyListView mListView;
    private List<GankModel> mGankModels;
    private NewListAdapter mAdapter;

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
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getData();
            }
        }, 300);
    }

    private void initListener() {
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
                mGankModels.clear();
                mGankModels.addAll(newResultModel.getResults().getAndroid());
                mGankModels.addAll(newResultModel.getResults().getIos());
                mGankModels.addAll(newResultModel.getResults().getExpand());
                mGankModels.addAll(newResultModel.getResults().getRecommendation());
                mGankModels.addAll(newResultModel.getResults().getVideo());

                if (!newResultModel.getResults().getWelfare().isEmpty()) {
                    String url = newResultModel.getResults().getWelfare().get(0).getUrl();
                    mSimpleDraweeView.setImageURI(Uri.parse(url));
                } else {
                    mSimpleDraweeView.setImageURI(Uri.parse("res://io.gank/" + R.drawable.def_image));
                }

                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.e(error.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);
            }

        });

    }

    private void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_view);
        mListView = (MyListView) view.findViewById(R.id.lv_list);
        mSimpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv_view);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.blue, R.color.yellow, R.color.green);

        mGankModels = new ArrayList<GankModel>();
        mAdapter = new NewListAdapter(mContext, mGankModels);
        mListView.setAdapter(mAdapter);

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
}
