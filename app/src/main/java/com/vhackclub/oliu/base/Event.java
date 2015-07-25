package com.vhackclub.oliu.base;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

@ParseClassName("Event")
public class Event extends ParseObject {

    public static enum TYPE {
        EAT;
    }

    public void setType(TYPE type) {
        put("type", type.name());
    }

    public TYPE getType() {
        return TYPE.valueOf(getString("type"));
    }

    public List<Suggestion> getLocationSuggestions() {
        return getList("suggestions");
    }

    public void setLocationSuggestions(List<Suggestion> suggestions) {
        put("suggestions", suggestions);
    }

    public void addLocationSuggestion(Suggestion suggestion) {
        add("suggestions", suggestion);
    }

    public void setComments(List<Comment> comments) {
        put("comments", comments);
    }

    public List<Comment> getComments(Comment comment) {
        return getList("comments");
    }

    public static ParseQuery<Event> getQuery() {
        return ParseQuery.getQuery(Event.class).include("suggestions").include("comments");
    }
}
