package com.vhackclub.oliu.base;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Suggestion")
public class Suggestion extends ParseObject {

    public String getUuidString() {
        return getString("uuid");
    }

    public void setUuidString(String uuid) {
        put("uuid", uuid);
    }

    public static ParseQuery<Suggestion> getQuery() {
        return ParseQuery.getQuery(Suggestion.class);
    }
}
