package com.vhackclub.oliu.base;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ParseClassName("Event")
public class Event extends ParseObject {

    public static enum TYPE {
        EAT, FUCK, WATCH;
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

    public void addMember(ParseUser user) { add("members", user); }

    public List<ParseUser> getMembers() {return getList("members"); }

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

    public String getGreeting() {
        return "Lets " + getType().toString() + " at " + getWhen().toString();
    }
}
