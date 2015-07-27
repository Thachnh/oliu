package com.vhackclub.oliu.base;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Comment")
public class Comment extends ParseObject {

    public String getText() {
        return getString("text");
    }

    public void setText(String text) {
        put("text", text);
    }

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public static ParseQuery<Comment> getQuery() {
        return ParseQuery.getQuery(Comment.class);
    }

    public String getPushDesc() {
        return getUser().getObjectId() + " comments: " + getText();
    }
}
