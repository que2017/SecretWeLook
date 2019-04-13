/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.widget.Toast;

import com.duiyi.secretwelook.activities.LoginActivity;
import com.duiyi.secretwelook.activities.TimeLineActivity;

/**
 * 程序入口函数
 *
 * @author zhang
 * @since 2019/4/5
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Config.checkPermission(this, Manifest.permission.READ_CONTACTS, Config.CONTACTS_PERMISSION);
        String token = Config.getCachedToken(this);
        String phoneMD5 = Config.getCachedPhoneMD5(this);
        if ((token != null) && (phoneMD5 != null)) {
            Intent intent = new Intent(this, TimeLineActivity.class);
            intent.putExtra(Config.KEY_TOKEN, token);
            intent.putExtra(Config.KEY_PHONE_MD5, phoneMD5);
            startActivity(intent);
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }

        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Config.CONTACTS_PERMISSION:
                if ((grantResults.length <= 0) || (grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, R.string.contacts_permission_not_open, Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
