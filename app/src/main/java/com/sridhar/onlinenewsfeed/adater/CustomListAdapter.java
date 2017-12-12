package com.sridhar.onlinenewsfeed.adater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.Html;
import android.text.format.DateUtils;
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

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Newsfeeds> movieItems;
    private RequestQueue requestQueue;
    private ImageLoader mImageLoader;
    private String ImageUrl = "",title="";
    Bitmap bitmaptwo;

    public CustomListAdapter(MainActivity mainActivity, List<Newsfeeds> newsList) {
        this.activity = mainActivity;
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.movieItems = newsList;
        Log.e("msg", "" + movieItems.size());
        requestQueue = VolleySingleton.getVolleySingletonInstance().getRequestQueue();
        mImageLoader = new ImageLoader(this.requestQueue,
                new LruBitmapCache());
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final Newsfeeds detailss = movieItems.get(position);
        if (convertView == null) {


            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_row, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.title_txt);
            holder.time_txt = (TextView) convertView.findViewById(R.id.time_txt);
            holder.content = (TextView) convertView.findViewById(R.id.content_txt);
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.share_button = (Button) convertView.findViewById(R.id.share_button);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(movieItems.get(position).getTitle());
        holder.content.setText(movieItems.get(position).getContent_text());
        holder.time_txt.setText("post: " + DateUtils.getRelativeTimeSpanString(Long.parseLong(movieItems.get(position).getTime())));

        holder.share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUrl = "";
                title="";
                title=detailss.getContent_text();
                ImageUrl = detailss.getImg_url();
                new BackgroundTask().execute(new String[]{ImageUrl});



            }
        });

       /* Glide.with(activity).load(movieItems.get(position).getImg_url())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);*/
        Glide.with(this.activity).
                load(movieItems.get(position).getImg_url())
                .thumbnail(Glide.with(this.activity)
                        .load(Integer.valueOf(R.raw.loading_thumbnail)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate().into(holder.imageView);


       /* mImageLoader.get(detailss.getImg_url(), ImageLoader.getImageListener(
                holder.imageView,R.raw.loading_thumbnail, R.drawable.ico_error));
        Cache cache = requestQueue.getCache();
        Cache.Entry entry = cache.get(detailss.getImg_url());
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            // cached response doesn't exists. Make a network call here
        }*/
        return convertView;
    }

    class ViewHolder {
        TextView title, time_txt;
        TextView content;
        ImageView imageView;
        Button share_button;
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
