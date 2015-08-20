package io.gank.util;


import com.orhanobut.logger.Logger;

import io.gank.BuildConfig;

/**
 * Created by satan on 2015/8/18.
 */
public class LogUtil {

    private static final String TAG = LogUtil.class.getSimpleName();

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Logger.e(tag, msg);
        }
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.e(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Logger.d(tag, msg);
        }
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.d(TAG, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Logger.w(tag, msg);
        }
    }

    public static void w(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.w(TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Logger.i(tag, msg);
        }
    }

    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.i(TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Logger.v(tag, msg);
        }
    }

    public static void v(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.v(TAG, msg);
        }
    }
}
