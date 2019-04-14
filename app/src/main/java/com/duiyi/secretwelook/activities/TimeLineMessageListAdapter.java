/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.duiyi.secretwelook.R;
import com.duiyi.secretwelook.net.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示朋友圈消息的adapter
 *
 * @author zhang
 * @since 2019/4/14
 */
public class TimeLineMessageListAdapter extends BaseAdapter {
    private static final String TAG = TimeLineMessageListAdapter.class.getSimpleName();

    private Context mContext;
    private List<Message> data = new ArrayList<>();

    public TimeLineMessageListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Message getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.timeline_list_cell, null);
            convertView.setTag(new ListCell((TextView) convertView.findViewById(R.id.cellLabel)));
        }
        ListCell listCell = (ListCell) convertView.getTag();
        listCell.getCellLabel().setText(getItem(position).getMsg());
        return convertView;
    }

    public void addAll(List<Message> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public Context getContext() {
        return mContext;
    }

    private static class ListCell {
        private TextView cellLabel;

        public ListCell(TextView tv) {
            cellLabel = tv;
        }

        public TextView getCellLabel() {
            return cellLabel;
        }
    }
}
