/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.activities;

import android.widget.TextView;

/**
 * ListView中的每个标签
 *
 * @author zhang
 * @since 2019/4/21
 */
public class ListCell {
    private TextView cellLabel;

    ListCell(TextView tv) {
        cellLabel = tv;
    }

    public TextView getCellLabel() {
        return cellLabel;
    }
}
