package com.vhackclub.oliu;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.vhackclub.oliu.base.BaseEvent;
import com.vhackclub.oliu.base.BaseLocation;
import com.vhackclub.oliu.base.BaseSuggestion;
import com.vhackclub.oliu.base.Comment;
import com.vhackclub.oliu.models.LocationSuggestion;
import com.vhackclub.oliu.models.Restaurant;

/**
 * Created by thachnh on 7/25/15.
 */
public class MyApplication  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());

        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(BaseEvent.class);
        ParseObject.registerSubclass(BaseLocation.class);
        ParseObject.registerSubclass(BaseSuggestion.class);
        ParseObject.registerSubclass(LocationSuggestion.class);
        ParseObject.registerSubclass(Restaurant.class);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this,
                "6YcCS8AtuxwZYPbsKjeaOdCip4IR4dvd0RcArorN",
                "9dDakcH3uqJtl6Z4BLrEy7H51ld06t7scLo9agfH"
        );
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);

        ParseFacebookUtils.initialize(this);
    }
}
