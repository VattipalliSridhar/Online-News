package com.sridhar.onlinenewsfeed.models;

import android.graphics.Bitmap;

/**
 * Created by 2136 on 11/9/2017.
 */

public class Newsfeeds
{
    String title;
    String content_text;
    String img_url;
    Bitmap bitmap;

    public Newsfeeds(String title, String content_text, String img_url, Bitmap bitmap) {
        this.title = title;
        this.content_text = content_text;
        this.img_url = img_url;
        this.bitmap = bitmap;
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

    public String getContent_text() {
        return content_text;
    }

    public void setContent_text(String content_text) {
        this.content_text = content_text;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
