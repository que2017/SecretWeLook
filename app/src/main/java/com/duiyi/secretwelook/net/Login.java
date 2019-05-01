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

    public Login(String phoneMD5, String code, final NetCallback callback) {
        if (callback == null) {
            return;
        }

        Map<String, String> values = new HashMap<>();
        values.put(Config.KEY_ACTION, Config.ACTION_LOGIN);
        values.put(Config.KEY_PHONE_MD5, phoneMD5);
        values.put(Config.KEY_CODE, code);

        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    switch (json.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            callback.onSuccess(json.getString(Config.KEY_TOKEN));
                            break;
                        case Config.RESULT_STATUS_FAIL:
                        default:
                            callback.onFail(Config.RESULT_STATUS_FAIL);
                            break;
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Parse result error: " + e.getMessage());
                    callback.onFail(Config.RESULT_STATUS_FAIL);
                }
            }

            @Override
            public void onFail(int errCode) {
                callback.onFail(errCode);
            }
        }, values).requestHttp();
    }
}
