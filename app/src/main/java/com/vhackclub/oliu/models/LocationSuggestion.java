package com.vhackclub.oliu.models;

import com.parse.ParseClassName;
import com.vhackclub.oliu.base.BaseLocation;
import com.vhackclub.oliu.base.BaseSuggestion;

/**
 * Created by geruk on 7/26/15.
 */
@ParseClassName("LocationSuggestion")
public class LocationSuggestion extends BaseSuggestion {
    public void setLocation(BaseLocation location) {
        put("location", location);
    }
    public void getLocation() {

    }
}
