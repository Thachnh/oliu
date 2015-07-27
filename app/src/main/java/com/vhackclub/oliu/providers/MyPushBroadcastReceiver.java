package com.vhackclub.oliu.providers;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseUser;
import com.vhackclub.oliu.EventPlannerActivity;

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
        JSONObject data = getDataFromIntent(intent);
        String userId = data.optString("user_id", "");
        if (userId.equals(ParseUser.getCurrentUser().getObjectId())) {
            return null;
        }
        return super.getNotification(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        JSONObject data = getDataFromIntent(intent);
        String channel = intent.getExtras().getString(PARSE_CHANNEL_KEY);

        if (channel != null) {
            if (channel.startsWith("event-")) {
                String eventId = channel.split("-")[1];
                Intent i = new Intent(context, EventPlannerActivity.class);
                Bundle b = new Bundle();
                b.putString("event_id", eventId);
                i.putExtras(b);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
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
