package com.vhackclub.oliu;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

/**
 * Created by thachnh on 7/25/15.
 */
public class MyApplication  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());

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
