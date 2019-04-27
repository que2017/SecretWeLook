/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.duiyi.secretwelook.Config;
import com.duiyi.secretwelook.R;
import com.duiyi.secretwelook.net.PubMessage;

/**
 * 发布消息的界面
 *
 * @author zhang
 * @since 2019/4/6
 */
public class PubMessageActivity extends Activity {
    private static final String TAG = PubMessageActivity.class.getSimpleName();

    private String mPhoneMD5;
    private String mToken;
    private EditText mMessageText;
    private Button mPubMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pub_message_layout);
        mMessageText = findViewById(R.id.messageText);
        mPubMessage = findViewById(R.id.pubMessage);
        Intent intent = getIntent();
        mPhoneMD5 = intent.getStringExtra(Config.KEY_PHONE_MD5);
        mToken = intent.getStringExtra(Config.KEY_TOKEN);

        mPubMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mMessageText.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(PubMessageActivity.this, R.string.message_cannot_be_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                final ProgressDialog pd = ProgressDialog.show(PubMessageActivity.this, getResources().getString(R.string.connecting),
                        getResources().getString(R.string.connecting_to_server));
                new PubMessage(mPhoneMD5, mToken, message, new PubMessage.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        pd.dismiss();
                        setResult(Config.ACTIVITY_NEED_REFERSH);
                        Toast.makeText(PubMessageActivity.this, R.string.publish_message_success, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new PubMessage.FailCallback() {
                    @Override
                    public void onFail(int errorCode) {
                        pd.dismiss();
                        if (errorCode == Config.RESULT_STATUS_INVALID_TOKEN) {
                            startActivity(new Intent(PubMessageActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(PubMessageActivity.this, R.string.publish_message_fail, Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                });
            }
        });
    }
}
