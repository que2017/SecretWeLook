/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.net;

/**
 * 网络通信请求回调函数
 *
 * @author zhang
 * @since 2019/5/1
 */
public interface NetCallback {
    /**
     * 返回成功的回调
     *
     * @param result 返回结果
     */
    void onSuccess(String result);

    /**
     * 返回失败的回调
     *
     * @param errCode 错误码
     */
    void onFail(int errCode);
}
