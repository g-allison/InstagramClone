package com.codepath.instagramclone;

import android.util.Log;

import java.util.Date;

public class TimeAgo {

    Date createdAt;

    int SECOND_MILLIS = 1000;
    int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    int DAY_MILLIS = 24 * HOUR_MILLIS;

    public TimeAgo(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String calculateTimeAgo() {
        createdAt.getTime();
        long time = createdAt.getTime();
        long now = System.currentTimeMillis();

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }
}
