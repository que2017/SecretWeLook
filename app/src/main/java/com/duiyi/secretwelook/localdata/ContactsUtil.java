/*
 * Copyright (c) 2019-2019.
 */

package com.duiyi.secretwelook.localdata;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.duiyi.secretwelook.Config;
import com.duiyi.secretwelook.tools.MD5Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取手机联系人
 *
 * @author zhang
 * @since 2019/4/7
 */
public class ContactsUtil {
    private static final String TAG = ContactsUtil.class.getSimpleName();

    public static String getContacts(Context context) {
        JSONArray jsonArray = new JSONArray();

        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        while (cursor.moveToNext()) {
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phone = phone.replace("+86", "");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Config.KEY_PHONE_MD5, MD5Tool.md5(phone));
            } catch (JSONException e) {
                Log.e(TAG, "JSON object error: " + e.getMessage());
            }
            jsonArray.put(jsonObject);
        }
        cursor.close();
        return jsonArray.toString();
    }
}
