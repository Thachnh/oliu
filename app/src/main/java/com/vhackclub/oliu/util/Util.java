package com.vhackclub.oliu.util;

import com.parse.ParsePush;
import com.vhackclub.oliu.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thachnh on 7/25/15.
 */
public class Util {
    public static int PICK_LOCATION = 11;

    public static void push(String channel, String action, String objectId, String alert) {
        try {
            ParsePush push = new ParsePush();
            push.setChannel(channel);
            JSONObject obj = new JSONObject();
            obj.put("action", action);
            obj.put("object_id", objectId);
            obj.put("alert", alert);
            push.setData(obj);
            push.sendInBackground();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
