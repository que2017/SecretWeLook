/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.net;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.duiyi.secretwelook.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 网络相关的基类
 *
 * @author zhang
 * @since 2019/4/5
 */
public class NetConnection {
    private static final String TAG = NetConnection.class.getSimpleName();
    private String mUrl;
    private HttpMethod mHttpMethod;
    private NetCallback mCallback;
    private Map<String, String> mValues;

    public NetConnection(String url, HttpMethod method, NetCallback callback, Map<String, String> values) {
        mUrl = url;
        mHttpMethod = method;
        mCallback = callback;
        mValues = values;
    }

    @SuppressLint("StaticFieldLeak")
    public void requestHttp() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                // 对请求参数进行包装
                StringBuilder paramStr = new StringBuilder();
                for (Map.Entry<String, String> entry : mValues.entrySet()) {
                    paramStr.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                Log.i(TAG, "Request params: " + paramStr);

                try {
                    // 发送http请求
                    URLConnection conn;
                    switch (mHttpMethod) {
                        case POST:
                            conn = new URL(mUrl).openConnection();
                            conn.setDoOutput(true);
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), Config.CHARSET));
                            bw.write(paramStr.toString());
                            bw.flush();
                            break;
                        case GET: // 默认方式为Get
                        default:
                            conn = new URL(mUrl + "?" + paramStr.toString()).openConnection();
                            break;
                    }
                    Log.i(TAG, "Request URL: " + conn.getURL());

                    // 获取http响应返回值
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Config.CHARSET));
                    String line;
                    StringBuilder result = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        result.append(line);
                    }
                    Log.i(TAG, "Result: " + result);

                    return result.toString();
                } catch (IOException e) {
                    Log.e(TAG, "connection error: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                if (mCallback == null) {
                    return;
                }
                if (result != null) {
                    mCallback.onSuccess(result);
                } else {
                    mCallback.onFail(Config.RESULT_STATUS_FAIL);
                }
                super.onPostExecute(result);
            }
        }.execute();
    }
}
