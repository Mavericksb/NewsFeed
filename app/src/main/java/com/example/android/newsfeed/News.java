package com.example.android.newsfeed;

import android.graphics.Bitmap;


/**
 * Created by ROBERTO on 04/04/2017.
 */

public class News {


    private String title;
    private String description;
    //private String image;
    private String image;
    private String publishedDate;

    public News (String title, String description, String image, String date){
        this.title = title;
        this.description = description;
        this.image = image;
        publishedDate = date;

    }

    public String getTitle(){
        return title;
    }

    public String getSubTitle(){
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getPublishedDate(){
        return publishedDate;
    }


}
