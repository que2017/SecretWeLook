/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.duiyi.secretwelook.Config;
import com.duiyi.secretwelook.R;
import com.duiyi.secretwelook.localdata.ContactsUtil;
import com.duiyi.secretwelook.net.UploadContacts;

/**
 * 展示动态内容的界面
 *
 * @author zhang
 * @since 2019/4/5
 */
public class TimeLineActivity extends ListActivity {
    private static final String TAG = TimeLineActivity.class.getSimpleName();

    private String mToken;
    private String mPhoneMD5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_layout);

        mPhoneMD5 = getIntent().getStringExtra(Config.KEY_PHONE_MD5);
        mToken = getIntent().getStringExtra(Config.KEY_TOKEN);
        new UploadContacts(mPhoneMD5, mToken, ContactsUtil.getContacts(this), new UploadContacts.SuccessCallback() {
            @Override
            public void onSuccess() {
                loadMessage();
            }
        }, new UploadContacts.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                if (errorCode == Config.RESULT_STATUS_INVALID_TOKEN) {
                    startActivity(new Intent(TimeLineActivity.this, LoginActivity.class));
                    finish();
                } else {
                    loadMessage();
                }
            }
        });
    }

    private void loadMessage() {
        Log.i(TAG, ">>>>>>start load message<<<<<<");
    }
}
