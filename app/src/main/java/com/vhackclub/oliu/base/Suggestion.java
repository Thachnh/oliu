package com.vhackclub.oliu.base;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Suggestion")
public class Suggestion extends ParseObject {

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

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
