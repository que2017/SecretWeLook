/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.net;

/**
 * 朋友圈消息数据
 *
 * @author zhang
 * @since 2019/4/14
 */
public class Message {
    private String msgId;
    private String msg;
    private String phoneMD5;

    public Message(String msgId, String msg, String phoneMD5) {
        this.msgId = msgId;
        this.msg = msg;
        this.phoneMD5 = phoneMD5;
    }

    public String getMsg() {
        return msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public String getPhoneMD5() {
        return phoneMD5;
    }
}
