/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 配置相关类
 *
 * @author zhang
 * @since 2019/4/5
 */
public class Config {
    private static final String TAG = Config.class.getSimpleName();

//    public static final String SERVER_URL = "http://demo.eoeschool.com/api/v1/nimings/io";
    public static final String SERVER_URL = "http://192.168.199.186:8080/SecretWeLookServer/api.jsp";
    public static final String KEY_ACTION = "action";
    public static final String KEY_PHONE = "phone";
    public static final String ACTION_GET_CODE = "send_pass";

    public static final String KEY_STATUS = "status";
    public static final int RESULT_STATUS_SUCCESS = 1;
    public static final int RESULT_STATUS_FAIL = 0;
    public static final int RESULT_STATUS_INVALID_TOKEN = 2;

    public static final String APP_ID = "com.duiyi.secretwelook";
    public static final String KEY_TOKEN = "token";
    public static final String CHARSET = "utf-8";

    /**
     * 功能描述
     *
     * @param context 上下文
     * @return 获取到的缓存token
     */
    public static String getCachedToken(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
    }

    /**
     * 缓存token
     *
     * @param context 上下文
     * @param token token
     */
    public static void cacheToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }
}
