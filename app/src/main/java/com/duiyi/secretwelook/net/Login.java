/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.net;

import android.util.Log;

import com.duiyi.secretwelook.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * http登陆
 * <p>
 * 请求值：
 * action=login
 * phone_md5=手机号的md5值
 * code=短信验证码
 * <p>
 * 返回值：
 * status=1成功，0失败
 * token=对应手机号的token值
 *
 * @author zhang
 * @since 2019/4/7
 */
public class Login {
    private static final String TAG = Login.class.getSimpleName();

    public static interface SuccessCallback {
        void onSuccess(String result);
    }

    public static interface FailCallback {
        void onFail();
    }

    public Login(String phoneMD5, String code, final SuccessCallback success, final FailCallback fail) {
        Map<String, String> values = new HashMap<>();
        values.put(Config.KEY_ACTION, Config.ACTION_LOGIN);
        values.put(Config.KEY_PHONE_MD5, phoneMD5);
        values.put(Config.KEY_CODE, code);

        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    switch (json.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (success != null) {
                                success.onSuccess(json.getString(Config.KEY_TOKEN));
                            }
                            break;
                        case Config.RESULT_STATUS_FAIL:
                        default:
                            if (fail != null) {
                                fail.onFail();
                            }
                            break;
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Parse result error: " + e.getMessage());
                    if (fail != null) {
                        fail.onFail();
                    }
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if (fail != null) {
                    fail.onFail();
                }
            }
        }, values).requestHttp();
    }
}
