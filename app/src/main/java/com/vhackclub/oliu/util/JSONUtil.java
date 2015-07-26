package com.vhackclub.oliu.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by duncapham on 7/25/15.
 */
public class JSONUtil {
    public static JSONObject getJsonObject(String keyWord, JSONObject obj) {
        try {
            JSONObject newObj = obj.getJSONObject(keyWord);
            return newObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getString(String keyWord, JSONObject obj) {
        try {
            String str = obj.getString(keyWord);
            return str;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
