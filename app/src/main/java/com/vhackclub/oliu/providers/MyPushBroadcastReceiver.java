package com.vhackclub.oliu.providers;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thachnh on 7/26/15.
 */
public class MyPushBroadcastReceiver extends ParsePushBroadcastReceiver {

    public static final String PARSE_DATA_KEY = "com.parse.Data";
    public static final String PARSE_CHANNEL_KEY = "com.parse.Channel";

    @Override
    protected Notification getNotification(Context context, Intent intent) {
        return super.getNotification(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        // Implement
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        JSONObject data = getDataFromIntent(intent);
        Log.d("get the push", "data " + data.toString());

        String channel = intent.getExtras().getString(PARSE_CHANNEL_KEY);
        if (channel != null) {
            if (channel.startsWith("event-")) {
                handleEventPush(data);
            }
        }

        super.onPushReceive(context, intent);
    }

    private void handleEventPush(JSONObject data) {
        String action = data.optString("action", "");
        if (action.equals("comment")) {

        }
    }

    private JSONObject getDataFromIntent(Intent intent) {
        JSONObject data = null;
        try {
            data = new JSONObject(intent.getExtras().getString(PARSE_DATA_KEY));
        } catch (JSONException e) {
            // Json was not readable...
        }
        return data;
    }
}
