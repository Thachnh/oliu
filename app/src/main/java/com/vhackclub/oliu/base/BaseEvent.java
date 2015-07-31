package com.vhackclub.oliu.base;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vhackclub.oliu.models.LocationSuggestion;

import java.util.Date;
import java.util.List;

@ParseClassName("BaseEvent")
public class BaseEvent extends ParseObject {

    public static enum TYPE {
        EAT
    }

    public void setType(TYPE type) {
        put("type", type.name());
    }

    public TYPE getType() {
        return TYPE.valueOf(getString("type"));
    }

    public void setWhen(Date date) { put("when", date); }

    public Date getWhen() { return getDate("when"); }

    public void setHost(ParseUser user) {
        put("host", user);
    }

    public ParseUser getHost() { return getParseUser("host"); }

    public void setMembers(List<ParseUser> members) { put("members", members); }

    public void addMember(ParseUser user) { addUnique("members", user); }

    public List<ParseUser> getMembers() {return getList("members"); }

    public void setWhere(BaseLocation location) {
        put("location", location);
    }

    public Object getWhere() {
        return get("location");
    }

    public List<LocationSuggestion> getLocationSuggestions() {
        return getList("locationSuggestions");
    }

    public void setLocationSuggestions(List<LocationSuggestion> suggestions) {
        put("locationSuggestions", suggestions);
    }

    public void addLocationSuggestion(LocationSuggestion suggestion) {
        add("locationSuggestions", suggestion);
    }

    public void setComments(List<Comment> comments) {
        put("comments", comments);
    }

    public List<Comment> getComments() {
        return getList("comments");
    }

    public void addComment(Comment comment) {
        add("comments", comment);
    }

    public static ParseQuery<BaseEvent> getQuery() {
        return ParseQuery.getQuery(BaseEvent.class).include("suggestions").include("comments");
    }

    public String getGreeting() {
        return "Lets " + getType().toString() + " at " + getWhen().toString();
    }

}
