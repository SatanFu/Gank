package io.gank.http;

import io.gank.model.NewResultModel;
import io.gank.model.RandomResultModel;
import io.gank.model.ResultModel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by satan on 2015/8/14.
 */
public interface GankService {


    @GET("/day/{year}/{month}/{day}")
    void getNewData(@Path("year") String year, @Path("month") String month, @Path("day") String day, Callback<NewResultModel> callback);

    @GET("/data/{type}/{count}/{page}")
    void getTypeData(@Path("type") String type, @Path("count") String count, @Path("page") String page, Callback<ResultModel> callback);

    @GET("/random/data/{type}/{count}")
    void getRandomData(@Path("type") String type, @Path("count") String count, Callback<ResultModel> callback);

}
