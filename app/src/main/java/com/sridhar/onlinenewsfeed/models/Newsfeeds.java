package com.sridhar.onlinenewsfeed.models;

import android.graphics.Bitmap;

/**
 * Created by 2136 on 11/9/2017.
 */

public class Newsfeeds
{
    String title;
    String content;
    String imgurl;
    Bitmap bitmap;
    String time;

    public String getTime() {
        return time;
    }

    public Newsfeeds(String title, String content_text, String img_url) {
        this.title = title;
        this.content = content_text;
        this.imgurl = img_url;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public Bitmap getBitmap() {

        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Newsfeeds() {
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content_text) {
        this.content = content_text;
    }

    public String getImg_url() {
        return imgurl;
    }

    public void setImg_url(String img_url) {
        this.imgurl = img_url;
    }
}
