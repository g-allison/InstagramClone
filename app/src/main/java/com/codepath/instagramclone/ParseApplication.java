package com.codepath.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("TkijDwI8QXpwo3dpvcedBv1clsZTI0vuDbceUktc")
                .clientKey("gQvOVKX1HB4izIRCP3oyT2WQVdKG0YXQe16pLC8g")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
