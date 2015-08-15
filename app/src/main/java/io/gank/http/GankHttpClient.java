package io.gank.http;

import com.orhanobut.logger.Logger;

import io.gank.model.NewResultModel;
import io.gank.model.RandomResultModel;
import io.gank.model.ResultModel;
import io.gank.util.StringUtils;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by satan on 2015/8/14.
 */
public class GankHttpClient {

    private static final String API_URL = "http://gank.avosapps.com/api";
    private static RestAdapter mRestAdapter;
    private static GankService mGankService;
    private static final String COUNT = "10";

    private static GankService getGankService() {
        if (mGankService == null) {
            if (mRestAdapter == null) {
                mRestAdapter = new RestAdapter.Builder().setEndpoint(API_URL).build();
            }
            mGankService = mRestAdapter.create(GankService.class);
        }
        return mGankService;
    }


    public static void getNewData(int year, int month, int day, Callback<NewResultModel> callback) {
        Logger.e(year + "-" + month + "-" + day);
        getGankService().getNewData(String.valueOf(year), StringUtils.addZeroForTime(month), StringUtils.addZeroForTime(day), callback);
    }

    public static void getTypeData(String type, int page, Callback<ResultModel> callback) {
        Logger.e(type + "-" + page);
        getGankService().getTypeData(type, COUNT, String.valueOf(page), callback);
    }

    public static void getRandomData(String type, Callback<RandomResultModel> callback) {
        Logger.e(type);
        getGankService().getRandomData(type, COUNT, callback);
    }
}
