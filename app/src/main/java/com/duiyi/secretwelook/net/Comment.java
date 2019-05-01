/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.net;

/**
 * 消息评论数据
 *
 * @author zhang
 * @since 2019/4/21
 */
public class Comment {
    private String mContent;
    private String mPhoneMD5;

    public Comment(String content, String phoneMD5) {
        mContent = content;
        mPhoneMD5 = phoneMD5;
    }

    public String getContent() {
        return mContent;
    }

    public String getPhoneMD5() {
        return mPhoneMD5;
    }
}
