package com.example.ankit.navigationdrawer;

/**
 * Created by Ankit on 31-01-2018.
 */

public class Event {
    String eTitle;
    String eDescription;
    String eImgurl;
    String eId;
    public void Event(){

    }

    public Event(String eTitle, String eDescription, String eImgurl, String eId) {
        this.eTitle = eTitle;
        this.eDescription = eDescription;
        this.eImgurl = eImgurl;
        this.eId = eId;
    }

    public String geteTitle() {
        return eTitle;
    }

    public String geteDescription() {
        return eDescription;
    }

    public String geteImgurl(){return eImgurl;}

    public String geteId(){return eImgurl;}
}
