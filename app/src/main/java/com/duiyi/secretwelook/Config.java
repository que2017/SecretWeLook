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
    public static final String KEY_PHONE_MD5 = "phone_md5";
    public static final String KEY_CODE = "code";
    public static final String KEY_TOKEN = "token";
    public static final String ACTION_GET_CODE = "send_pass";
    public static final String ACTION_LOGIN = "login";

    public static final String KEY_STATUS = "status";
    public static final int RESULT_STATUS_SUCCESS = 1;
    public static final int RESULT_STATUS_FAIL = 0;
    public static final int RESULT_STATUS_INVALID_TOKEN = 2;

    public static final String APP_ID = "com.duiyi.secretwelook";
    public static final String CHARSET = "utf-8";

    /**
     * 获取缓存的token
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

    /**
     * 获取缓存的手机号MD5值
     *
     * @param context 上下文
     * @return 获取到的缓存手机号MD5值
     */
    public static String getCachedPhoneMD5(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PHONE_MD5, null);
    }

    /**
     * 缓存手机号的MD5
     *
     * @param context 上下文
     * @param phoneMD5 手机号的md5
     */
    public static void cachePhoneMD5(Context context, String phoneMD5) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_PHONE_MD5, phoneMD5);
        editor.apply();
    }
}
