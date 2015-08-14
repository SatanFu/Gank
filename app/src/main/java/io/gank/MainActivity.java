package io.gank;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import io.gank.fragment.NewFragment;
import io.gank.fragment.RandomFragment;
import io.gank.fragment.TypeFragment;
import io.gank.http.GankHttpClient;
import io.gank.model.NewResultModel;
import io.gank.model.RandomResultModel;
import io.gank.model.ResultModel;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private boolean isFinish = false;
    private Context mContext;
    private Fragment mThisFragment; // 当前页面
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        initData();
    }

    private void initData() {

//        GankHttpClient.getNewData(2015, 8, 14, new Callback<NewResultModel>() {
//            @Override
//            public void success(NewResultModel newResultModel, Response response) {
//                Logger.e(newResultModel.toString());
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//            }
//        });
//
//        for (final String type : getResources().getStringArray(R.array.type)) {
//            GankHttpClient.getTypeData(type, 1, new Callback<ResultModel>() {
//                @Override
//                public void success(ResultModel resultModel, Response response) {
//                    Logger.e(type + " -- " + resultModel.toString());
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//
//                }
//            });
//        }

        GankHttpClient.getRandomData("Android", new Callback<RandomResultModel>() {
            @Override
            public void success(RandomResultModel resultModel, Response response) {
                Logger.e(resultModel.toString() + "---" + resultModel.getResults().size());
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.e(error.getMessage() + "  " + error.getUrl() + "  " + error.toString());
            }
        });

    }

    private void initView() {

        mFragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        mThisFragment = NewFragment.newInstance();
        mFragmentTransaction.add(R.id.content, mThisFragment);
        mFragmentTransaction.commit();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setTitle(getResources().getString(R.string.new_data));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerContent(mNavigationView);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_new:
                                switchContent(NewFragment.newInstance());
                                mToolbar.setTitle(getResources().getString(R.string.new_data));
                                break;
                            case R.id.nav_type:
                                switchContent(TypeFragment.newInstance());
                                mToolbar.setTitle(getResources().getString(R.string.type_data));
                                break;
                            case R.id.nav_random:
                                switchContent(RandomFragment.newInstance());
                                mToolbar.setTitle(getResources().getString(R.string.random_data));
                                break;
                            case R.id.nav_about:
                                switchContent(RandomFragment.newInstance());
                                mToolbar.setTitle(getResources().getString(R.string.about));
                                break;
                        }
                        return true;
                    }
                });
    }

    /**
     * 跳转页面
     *
     * @param to
     */
    public void switchContent(Fragment to) {
        if (mThisFragment != to) {
            mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            // 先判断是否被add过
            if (!to.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                mFragmentTransaction.hide(mThisFragment).add(R.id.content, to);
            } else {
                // 隐藏当前的fragment，显示下一个
                mFragmentTransaction.hide(mThisFragment).show(to);
            }
            mFragmentTransaction.commit();
            mThisFragment = to;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (isFinish) {
                finish();
            } else {
                Toast.makeText(mContext, getResources().getString(R.string.back), Toast.LENGTH_SHORT).show();
                new Thread() {
                    public void run() {
                        isFinish = true;
                        try {
                            Thread.sleep(2000);
                            isFinish = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
