/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.duiyi.secretwelook.Config;
import com.duiyi.secretwelook.R;
import com.duiyi.secretwelook.net.GetVerificationCode;
import com.duiyi.secretwelook.net.Login;
import com.duiyi.secretwelook.tools.MD5Tool;

/**
 * 登陆界面
 *
 * @author zhang
 * @since 2019/4/5
 */
public class LoginActivity extends Activity implements View.OnClickListener {
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
        mGetVerificationCode.setOnClickListener(this);
        mLogin.setOnClickListener(this);
    }

    private int getVerificationMaxWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return (int) Math.floor((metrics.widthPixels - SPACE_MARGIN * metrics.density) * MAX_WIDTH);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getVerificationCode:
                getVerifyCode();
                break;
            case R.id.login:
                loginWeLook();
                break;
            default:
                break;
        }
    }

    private void loginWeLook() {
        String phone = mPhoneNumber.getText().toString();
        String code = mVerificationCode.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, R.string.phone_number_cannot_be_empty, Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, R.string.verify_code_cannot_be_empty, Toast.LENGTH_LONG).show();
            return;
        }

        final String phoneMD5 = MD5Tool.md5(phone);
        final ProgressDialog pd = ProgressDialog.show(this, getResources().getString(R.string.connecting),
                getResources().getString(R.string.connecting_to_server));
        new Login(phoneMD5, code, new Login.SuccessCallback() {
            @Override
            public void onSuccess(String token) {
                pd.dismiss();

                // 缓存返回的token以及手机号md5值
                Config.cacheToken(LoginActivity.this, token);
                Config.cachePhoneMD5(LoginActivity.this, phoneMD5);

                Intent intent = new Intent(LoginActivity.this, TimeLineActivity.class);
                intent.putExtra(Config.KEY_TOKEN, token);
                intent.putExtra(Config.KEY_PHONE_MD5, phoneMD5);
                startActivity(intent);

                finish();
            }
        }, new Login.FailCallback() {
            @Override
            public void onFail() {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, R.string.fail_to_login, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getVerifyCode() {
        String phone = mPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, R.string.phone_number_cannot_be_empty, Toast.LENGTH_LONG).show();
            return;
        }

        final ProgressDialog pd = ProgressDialog.show(this, getResources().getString(R.string.connecting),
                getResources().getString(R.string.connecting_to_server));
        new GetVerificationCode(phone, new GetVerificationCode.SuccessCallback() {
            @Override
            public void onSuccess() {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, R.string.success_to_get_verification_code, Toast.LENGTH_LONG).show();
            }
        }, new GetVerificationCode.FailCallback() {
            @Override
            public void onFail() {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, R.string.fail_to_get_verification_code, Toast.LENGTH_LONG).show();
            }
        });
    }
}
