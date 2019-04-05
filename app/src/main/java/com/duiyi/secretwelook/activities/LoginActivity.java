/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.activities;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.duiyi.secretwelook.R;

/**
 * 登陆界面
 *
 * @author zhang
 * @since 2019/4/5
 */
public class LoginActivity extends Activity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int SPACE_MARGIN = 24 * 2 + 8;
    private static final double MAX_WIDTH = 2.0 / 3.0;
    private EditText mPhoneNumber;
    private EditText mVerificationCode;
    private TextView mGetVerificationCode;
    private Button mLogin;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mVerificationCode.setMaxWidth(getVerificationMaxWidth());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        mPhoneNumber = findViewById(R.id.phoneNumber);
        mVerificationCode = findViewById(R.id.verificationCode);
        mGetVerificationCode = findViewById(R.id.getVerificationCode);
        mLogin = findViewById(R.id.login);

        mVerificationCode.setMaxWidth(getVerificationMaxWidth());
    }

    private int getVerificationMaxWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return (int) Math.floor((metrics.widthPixels - SPACE_MARGIN * metrics.density) * MAX_WIDTH);
    }
}
