/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.activities;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.duiyi.secretwelook.Config;
import com.duiyi.secretwelook.R;
import com.duiyi.secretwelook.localdata.ContactsUtil;
import com.duiyi.secretwelook.net.Message;
import com.duiyi.secretwelook.net.NetCallback;
import com.duiyi.secretwelook.net.TimeLine;
import com.duiyi.secretwelook.net.UploadContacts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private TimeLineMessageListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_layout);

        mAdapter = new TimeLineMessageListAdapter(this);
        setListAdapter(mAdapter);

        final ProgressDialog pd = ProgressDialog.show(this, getResources().getString(R.string.connecting),
                getResources().getString(R.string.connecting_to_server));
        mPhoneMD5 = getIntent().getStringExtra(Config.KEY_PHONE_MD5);
        mToken = getIntent().getStringExtra(Config.KEY_TOKEN);
        new UploadContacts(mPhoneMD5, mToken, ContactsUtil.getContacts(this), new NetCallback() {
            @Override
            public void onSuccess(String result) {
                pd.dismiss();
                loadMessage();
            }

            @Override
            public void onFail(int errCode) {
                pd.dismiss();
                if (errCode == Config.RESULT_STATUS_INVALID_TOKEN) {
                    startActivity(new Intent(TimeLineActivity.this, LoginActivity.class));
                    finish();
                } else {
                    loadMessage();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuPublishMessage:
                Intent intent = new Intent(TimeLineActivity.this, PubMessageActivity.class);
                intent.putExtra(Config.KEY_PHONE_MD5, mPhoneMD5);
                intent.putExtra(Config.KEY_TOKEN, mToken);
                startActivityForResult(intent, 0);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Config.ACTIVITY_NEED_REFERSH:
                loadMessage();
                break;
            default:
                break;
        }
    }

    private void loadMessage() {
        Log.i(TAG, ">>>>>>start load message<<<<<<");
        final ProgressDialog pd = ProgressDialog.show(this, getResources().getString(R.string.connecting),
                getResources().getString(R.string.connecting_to_server));
        new TimeLine(mPhoneMD5, mToken, 1, 20, new NetCallback() {
            @Override
            public void onSuccess(String result) {
                pd.dismiss();

                List<Message> msg = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(result);
                    JSONArray msgArray = json.getJSONArray(Config.KEY_TIME_LINE);
                    for (int i = 0; i < msgArray.length(); i++) {
                        JSONObject obj = msgArray.getJSONObject(i);
                        msg.add(new Message(
                                obj.getString(Config.KEY_MSG_ID),
                                obj.getString(Config.KEY_MSG),
                                obj.getString(Config.KEY_PHONE_MD5)
                        ));
                    }
                } catch (JSONException e) {
                    Log.i(TAG, "Parse result error: " + e.getMessage());
                }
                mAdapter.clear();
                mAdapter.addAll(msg);
            }

            @Override
            public void onFail(int errCode) {
                pd.dismiss();
                if (errCode == Config.RESULT_STATUS_INVALID_TOKEN) {
                    startActivity(new Intent(TimeLineActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(TimeLineActivity.this, R.string.fail_to_load_timeline_data, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Message msg = mAdapter.getItem(position);
        Intent intent = new Intent(TimeLineActivity.this, MessageActivity.class);
        intent.putExtra(Config.KEY_MSG, msg.getMsg());
        intent.putExtra(Config.KEY_MSG_ID, msg.getMsgId());
        intent.putExtra(Config.KEY_PHONE_MD5, msg.getPhoneMD5());
        intent.putExtra(Config.KEY_TOKEN, mToken);
        startActivity(intent);
    }
}
