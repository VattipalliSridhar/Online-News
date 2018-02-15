package com.sridhar.onlinenewsfeed.adater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sridhar.onlinenewsfeed.R;
import com.sridhar.onlinenewsfeed.activities.MainActivity;
import com.sridhar.onlinenewsfeed.models.Newsfeeds;
import com.sridhar.onlinenewsfeed.singletonclasses.VolleySingleton;
import com.sridhar.onlinenewsfeed.utils.LruBitmapCache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import cz.msebera.android.httpclient.client.methods.HttpGet;

/**
 * Created by 2136 on 11/9/2017.
 */

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.MyViewHolder> {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Newsfeeds> movieItems;
    private RequestQueue requestQueue;
    private ImageLoader mImageLoader;
    private String ImageUrl = "",title="";
    Bitmap bitmaptwo;
    private int width, height;
    public CustomListAdapter(MainActivity mainActivity, List<Newsfeeds> newsList)
    {
        this.movieItems = newsList;
        Log.e("msg", "" + movieItems.size());
        requestQueue = VolleySingleton.getVolleySingletonInstance().getRequestQueue();
        mImageLoader = new ImageLoader(this.requestQueue,
                new LruBitmapCache());
        this.activity = mainActivity;

        DisplayMetrics metrics = mainActivity.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;

    }

    @Override
    public CustomListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Newsfeeds detailss = movieItems.get(position);
        holder.title.setText(movieItems.get(position).getTitle());
        holder.content_txt.setText(movieItems.get(position).getContent());
        holder.thumbnail.getLayoutParams().width=width;

       /* Glide.with(this.activity).
                load(movieItems.get(position).getImg_url())
                .thumbnail(Glide.with(this.activity)
                        .load(Integer.valueOf(R.raw.loading_thumbnail)))
                .dontAnimate().into(holder.thumbnail);*/


        mImageLoader.get(movieItems.get(position).getImg_url(), ImageLoader.getImageListener(
                holder.thumbnail,R.raw.loading_thumbnail, R.drawable.ico_error));
        Cache cache = requestQueue.getCache();
        Cache.Entry entry = cache.get(movieItems.get(position).getImg_url());
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            // cached response doesn't exists. Make a network call here
        }


        holder.share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUrl = "";
                title="";
                title=detailss.getContent();
                ImageUrl = detailss.getImg_url();
                new BackgroundTask().execute(new String[]{ImageUrl});



            }
        });
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, time_txt;
        TextView content_txt;
        ImageView thumbnail;
        Button share_button;

        public MyViewHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title_txt);
            content_txt = (TextView) itemView.findViewById(R.id.content_txt);
            share_button = (Button) itemView.findViewById(R.id.share_button);
        }
    }



    private class BackgroundTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            return m3176a((String[]) params);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bitmaptwo = bitmap;


            try {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/jpeg");
                bitmaptwo.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                intent.putExtra("android.intent.extra.STREAM", Uri.parse(MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmaptwo, "Title", null)));
                intent.putExtra(Intent.EXTRA_TEXT,title);
                activity.startActivity(Intent.createChooser(intent, "Select"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected Bitmap m3176a(String... strArr) {
            return DownloadImage(strArr[0]);
        }
    }

    private Bitmap DownloadImage(String str) {
        Bitmap decodeStream;
        IOException e;
        InputStream OpenHttpConnection = null;
        try {
            OpenHttpConnection = OpenHttpConnection(str);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        decodeStream = BitmapFactory.decodeStream(OpenHttpConnection);
        if (OpenHttpConnection != null) {
            try {
                OpenHttpConnection.close();
            } catch (IOException e2) {
                e = e2;
                Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return decodeStream;
            }
        }
        return decodeStream;
    }

    private InputStream OpenHttpConnection(String str) throws IOException {
        URLConnection openConnection = null;
        try {
            openConnection = new URL(str).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (openConnection instanceof HttpURLConnection) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
                httpURLConnection.setAllowUserInteraction(false);
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.setRequestMethod(HttpGet.METHOD_NAME);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    return httpURLConnection.getInputStream();
                }
                return null;
            } catch (Exception e) {
                throw new IOException("Error connecting");
            }
        }
        throw new IOException("Not an HTTP connection");
    }
}
