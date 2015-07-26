package com.vhackclub.oliu.models;

import com.parse.ParseClassName;
import com.parse.ParseQuery;
import com.vhackclub.oliu.base.BaseLocation;
import java.io.Serializable;

/**
 * Created by duncapham on 7/25/15.
 */
@ParseClassName("Restaurant")
public class Restaurant extends BaseLocation implements Serializable{

    public static Restaurant createInstance() {
        Restaurant a = new Restaurant();
        a.setName("Ten nha hang");
        a.setAddress("111 duong Road, thanh pho City");
        a.setCategory("An cac");
        a.setPhone("111111111");
        a.setRating("3.0");
        a.setTier("$$$");
        a.setStatus("CAC ASD CAC ASD CAC ASD CAC ASD CAC ASD ");
        return a;
    }

    public String getVenueUrl() { return (String) get("venueUrl"); }

    public void setVenueUrl(String venueUrl) { put("venueUrl", venueUrl); }

    public String getId() { return (String) get("id"); }

    public void setId(String id) { put("id", id); }

    public String getName() { return (String) get("name") ;}

    public void setName(String name) { put("name", name); }

    public String getPhone() { return (String) get("phone"); }

    public void setPhone(String phone) {
        put("phone", phone);
    }

    public String getAddress() {
        return (String) get("address");
    }

    public void setAddress(String address) {
        put("address", address);
    }

    public String getCategory() {
        return (String) get("category");
    }

    public void setCategory(String category) {
        put("category", category);
    }

    public String getCanonicalUrl() {
        return (String) get("canonicalUrl");
    }

    public void setCanonicalUrl(String canonicalUrl) {
        put("canonicalUrl", canonicalUrl);
    }

    public String getTier() {
        return (String) get("tier");
    }

    public void setTier(String tier) {
        put("tier", tier);
    }

    public String getRating() {
        return (String) get("rating");
    }

    public void setRating(String rating) {
        put("rating", rating);
    }

    public String getStatus() {
        return (String) get("status");
    }

    public void setStatus(String status) { put("status", status); }

}
