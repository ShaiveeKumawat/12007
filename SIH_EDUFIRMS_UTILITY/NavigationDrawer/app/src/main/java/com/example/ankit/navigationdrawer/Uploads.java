package com.example.ankit.navigationdrawer;

/**
 * Created by PARI on 11-03-2018.
 */

public class Uploads {

    public String name;
    public String url;
    public String id;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Uploads(){

    }

    public Uploads(String name, String url, String id) {
        this.name = name;
        this.url = url;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

}