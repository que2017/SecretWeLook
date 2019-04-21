/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.activities;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.duiyi.secretwelook.Config;
import com.duiyi.secretwelook.R;
import com.duiyi.secretwelook.net.Comment;
import com.duiyi.secretwelook.net.GetComment;
import com.duiyi.secretwelook.net.PubComment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private String mToken;

    private TextView mMessage;
    private EditText mCommentText;

    private CommentsAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.message_layout);
        mAdapter = new CommentsAdapter(this);
        setListAdapter(mAdapter);

        mMessage = findViewById(R.id.message);
        mCommentText = findViewById(R.id.comment);

        Intent data = getIntent();
        mMsg = data.getStringExtra(Config.KEY_MSG);
        mMsgId = data.getStringExtra(Config.KEY_MSG_ID);
        mPhoneMD5 = data.getStringExtra(Config.KEY_PHONE_MD5);
        mToken = data.getStringExtra(Config.KEY_TOKEN);

        mMessage.setText(mMsg);

        loadComments();

        findViewById(R.id.sendMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mCommentText.getText())) {
                    Toast.makeText(MessageActivity.this, R.string.comment_cannot_be_empty, Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog pd = ProgressDialog.show(MessageActivity.this, getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));
                new PubComment(Config.getCachedPhoneMD5(MessageActivity.this), mToken, mCommentText.getText().toString(), mMsgId, new PubComment.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        pd.dismiss();
                        mCommentText.setText("");
                        loadComments();
                    }
                }, new PubComment.FailCallback() {
                    @Override
                    public void onFail(int errorCode) {
                        pd.dismiss();
                        if (errorCode == Config.RESULT_STATUS_INVALID_TOKEN) {
                            startActivity(new Intent(MessageActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(MessageActivity.this, R.string.fail_to_publish_comment, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void loadComments() {
        final ProgressDialog pd = ProgressDialog.show(this, getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));
        new GetComment(mPhoneMD5, mToken, 1, 20, mMsgId, new GetComment.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                pd.dismiss();
                mAdapter.clear();
                try {
                    List<Comment> list = new ArrayList<>();
                    JSONObject json = new JSONObject(result);
                    JSONArray array = json.getJSONArray(Config.KEY_COMMENTS);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject item = array.getJSONObject(i);
                        Comment comment = new Comment(item.getString(Config.KEY_CONTENT), item.getString(Config.KEY_PHONE_MD5));
                        list.add(comment);
                    }
                    mAdapter.addAll(list);
                } catch (JSONException e) {
                    Log.e(TAG, "Parse result error: " + e.getMessage());
                }
            }
        }, new GetComment.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                pd.dismiss();
                if (errorCode == Config.RESULT_STATUS_INVALID_TOKEN) {
                    startActivity(new Intent(MessageActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(MessageActivity.this, R.string.fail_to_get_comments, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
