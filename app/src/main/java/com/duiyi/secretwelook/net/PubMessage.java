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

    public PubMessage(String phoneMD5, String token, String msg, final NetCallback callback) {
        if (callback == null) {
            return;
        }

        Map<String, String> values = new HashMap<>();
        values.put(Config.KEY_ACTION, Config.KEY_PUBLISH);
        values.put(Config.KEY_PHONE_MD5, phoneMD5);
        values.put(Config.KEY_TOKEN, token);
        values.put(Config.KEY_MSG, msg);

        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    switch (json.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            callback.onSuccess(result);
                            break;
                        case Config.RESULT_STATUS_INVALID_TOKEN:
                            callback.onFail(Config.RESULT_STATUS_INVALID_TOKEN);
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
