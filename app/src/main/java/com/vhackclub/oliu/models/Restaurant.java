package com.vhackclub.oliu.models;

import com.vhackclub.oliu.base.BaseLocation;

/**
 * Created by duncapham on 7/25/15.
 */
public class Restaurant extends BaseLocation {
    private String id;
    private String name;
    private String phone;
    private String address;
    private String category;
    private String canonicalUrl;
    private String tier;
    private String rating;
    private String status;
    private String venueUrl;

    public String getVenueUrl() { return venueUrl; }

    public void setVenueUrl(String venueUrl) { this.venueUrl = venueUrl; }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public void setCanonicalUrl(String canonicalUrl) {
        this.canonicalUrl = canonicalUrl;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
