package com.vhackclub.oliu.base;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("BaseSuggestion")
public class BaseSuggestion extends ParseObject {

    public void setEvent(BaseEvent event) {
        put("event", event);
    }

    public Object getEvent() {
        return get("event");
    }

    public void setVoters(List<ParseUser> voters) {
        put("voters", voters);
    }

    public List<ParseUser> getVoters() {
        return getList("voters");
    }

    public static ParseQuery<BaseSuggestion> getQuery() {
        return ParseQuery.getQuery(BaseSuggestion.class);
    }
}
