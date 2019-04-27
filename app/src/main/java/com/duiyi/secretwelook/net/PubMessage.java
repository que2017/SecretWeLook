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
 * 发布消息的http通信类
 *
 * @author zhang
 * @since 2019/4/27
 */
public class PubMessage {
    private static final String TAG = PubMessage.class.getSimpleName();

    public static interface SuccessCallback {
        void onSuccess(String result);
    }

    public static interface FailCallback {
        void onFail(int errorCode);
    }

    public PubMessage(String phoneMD5, String token, String msg, final SuccessCallback success, final FailCallback fail) {
        Map<String, String> values = new HashMap<>();
        values.put(Config.KEY_ACTION, Config.KEY_PUBLISH);
        values.put(Config.KEY_PHONE_MD5, phoneMD5);
        values.put(Config.KEY_TOKEN, token);
        values.put(Config.KEY_MSG, msg);

        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    switch (json.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (success != null) {
                                success.onSuccess(result);
                            }
                            break;
                        case Config.RESULT_STATUS_INVALID_TOKEN:
                            if (fail != null) {
                                fail.onFail(Config.RESULT_STATUS_INVALID_TOKEN);
                            }
                            break;
                        case Config.RESULT_STATUS_FAIL:
                        default:
                            if (fail != null) {
                                fail.onFail(Config.RESULT_STATUS_FAIL);
                            }
                            break;
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Parse result error: " + e.getMessage());
                    if (fail != null) {
                        fail.onFail(Config.RESULT_STATUS_FAIL);
                    }
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if (fail != null) {
                    fail.onFail(Config.RESULT_STATUS_FAIL);
                }
            }
        }, values).requestHttp();
    }
}