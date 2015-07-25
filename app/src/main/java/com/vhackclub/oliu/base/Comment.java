package com.vhackclub.oliu.base;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Comment")
public class Comment extends ParseObject {

    public String getUuidString() {
        return getString("uuid");
    }

    public void setUuidString(String uuid) {
        put("uuid", uuid);
    }

    public static ParseQuery<Comment> getQuery() {
        return ParseQuery.getQuery(Comment.class);
    }
}
