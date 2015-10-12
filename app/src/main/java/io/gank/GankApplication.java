package io.gank;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;

import io.gank.model.NewResultModel;
import io.gank.util.StringUtils;

/**
 * Created by Administrator on 2015/8/15.
 */
public class GankApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static NewResultModel getOldGank(Context context) {
        NewResultModel newResultModels;
        SharedPreferences preferences = context.getSharedPreferences("gank", MODE_PRIVATE);
        String gankStr = preferences.getString("old_gank", "");
        if (StringUtils.isEmpty(gankStr)) {
            newResultModels = new NewResultModel();
        } else {
            newResultModels = JSON.parseObject(gankStr, NewResultModel.class);
        }
        return newResultModels;
    }

    public static void setOldGank(Context context, String json) {
        SharedPreferences preferences = context.getSharedPreferences("gank", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("old_gank", json);
        editor.commit();
    }

}
