/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.duiyi.secretwelook.activities.LoginActivity;
import com.duiyi.secretwelook.activities.TimeLineActivity;

/**
 * 程序入口函数
 *
 * @author zhang
 * @since 2019/4/5
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String token = Config.getCachedToken(this);
        if (token != null) {
            Intent intent = new Intent(this, TimeLineActivity.class);
            intent.putExtra(Config.KEY_TOKEN, token);
            startActivity(intent);
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
