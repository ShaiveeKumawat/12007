package com.example.ankit.navigationdrawer;

/**
 * Created by Ankit on 24-03-2018.
 */

public class MessageClass {
    String message;
    String date_time;

    public MessageClass(String message,String date_time) {
        this.message = message;
        this.date_time = date_time;
    }

    public String getMessage() {
        return message;
    }
    public String getDate_time() {
        return date_time;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setDate_time(String date_time) {
        this.date_time =date_time;
    }
}
