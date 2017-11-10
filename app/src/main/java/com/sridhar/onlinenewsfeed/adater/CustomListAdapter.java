package com.sridhar.onlinenewsfeed.adater;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sridhar.onlinenewsfeed.R;
import com.sridhar.onlinenewsfeed.activities.MainActivity;
import com.sridhar.onlinenewsfeed.models.Newsfeeds;
import com.sridhar.onlinenewsfeed.singletonclasses.VolleySingleton;
import com.sridhar.onlinenewsfeed.utils.LruBitmapCache;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by 2136 on 11/9/2017.
 */

public class CustomListAdapter extends BaseAdapter
{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Newsfeeds> movieItems;
    private RequestQueue requestQueue;
    private ImageLoader mImageLoader;
    public CustomListAdapter(MainActivity mainActivity, List<Newsfeeds> newsList)
    {
        this.activity = mainActivity;
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.movieItems = newsList;
        Log.e("msg",""+movieItems.size());
        requestQueue= VolleySingleton.getVolleySingletonInstance().getRequestQueue();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        Newsfeeds detailss=movieItems.get(position);
        if (convertView == null) {


            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_row, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.title_txt);
            holder.content = (TextView) convertView.findViewById(R.id.content_txt);
            holder.imageView=(ImageView)convertView.findViewById(R.id.thumbnail);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(detailss.getTitle());
        holder.content.setText(detailss.getContent_text());
       /* Glide.with(activity).load(detailss.getImg_url())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);*/
      // holder.imageView.setImageBitmap(detailss.getBitmap());

        mImageLoader.get(detailss.getImg_url(), ImageLoader.getImageListener(
                holder.imageView,R.drawable.ico_loading, R.drawable.ico_error));
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
        }
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView content;
        ImageView imageView;
    }
}
