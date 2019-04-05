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
    public static final String APP_ID = "com.duiyi.secretwelook";
    public static final String KEY_TOKEN = "token";

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
