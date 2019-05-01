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
import com.duiyi.secretwelook.net.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论列表adapter
 *
 * @author zhang
 * @since 2019/4/21
 */
public class CommentsAdapter extends BaseAdapter {
    private static final String TAG = CommentsAdapter.class.getSimpleName();

    private Context mContext;
    private List<Comment> comments = new ArrayList<>();

    public CommentsAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Comment getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comments_list_cell, null);
            convertView.setTag(new ListCell((TextView) convertView.findViewById(R.id.cellLabel)));
        }
        ListCell listCell = (ListCell) convertView.getTag();
        Comment comment = getItem(position);
        listCell.getCellLabel().setText(comment.getContent());
        return convertView;
    }

    public void addAll(List<Comment> data) {
        comments.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        comments.clear();
        notifyDataSetChanged();
    }

    public Context getContext() {
        return mContext;
    }
}
