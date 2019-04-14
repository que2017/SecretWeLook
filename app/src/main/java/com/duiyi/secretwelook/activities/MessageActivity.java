/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.duiyi.secretwelook.Config;
import com.duiyi.secretwelook.R;

/**
 * 展示消息的界面
 *
 * @author zhang
 * @since 2019/4/6
 */
public class MessageActivity extends ListActivity {
    private static final String TAG = MessageActivity.class.getSimpleName();

    private String mMsg;
    private String mMsgId;
    private String mPhoneMD5;

    private TextView mMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.message_layout);
        mMessage = findViewById(R.id.message);

        Intent data = getIntent();
        mMsg = data.getStringExtra(Config.KEY_MSG);
        mMsgId = data.getStringExtra(Config.KEY_MSG_ID);
        mPhoneMD5 = data.getStringExtra(Config.KEY_PHONE_MD5);

        mMessage.setText(mMsg);
    }
}
