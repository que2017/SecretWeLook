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
 * 获取短信验证码
 *
 * @author zhang
 * @since 2019/4/6
 */
public class GetVerificationCode {
    private static final String TAG = GetVerificationCode.class.getSimpleName();

    public static interface SuccessCallback {
        void onSuccess();
    }

    public static interface FailCallback {
        void onFail();
    }

    public GetVerificationCode(String phone, final SuccessCallback success, final FailCallback fail) {
        Map<String, String> values = new HashMap<>();
        values.put(Config.KEY_ACTION, Config.ACTION_GET_CODE);
        values.put(Config.KEY_PHONE, phone);

        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (success != null) {
                                success.onSuccess();
                            }
                            break;
                        case Config.RESULT_STATUS_FAIL:
                        case Config.RESULT_STATUS_INVALID_TOKEN:
                        default:
                            if (fail != null) {
                                fail.onFail();
                            }
                            break;
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Parse result error: " + e.getMessage());
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
