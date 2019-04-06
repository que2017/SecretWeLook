/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.duiyi.secretwelook.R;

/**
 * 展示动态内容的界面
 *
 * @author zhang
 * @since 2019/4/5
 */
public class TimeLineActivity extends ListActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_layout);
    }
}
