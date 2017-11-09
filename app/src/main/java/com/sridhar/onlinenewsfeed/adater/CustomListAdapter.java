package com.sridhar.onlinenewsfeed.adater;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sridhar.onlinenewsfeed.R;
import com.sridhar.onlinenewsfeed.activities.MainActivity;
import com.sridhar.onlinenewsfeed.models.Newsfeeds;

import java.util.List;

/**
 * Created by 2136 on 11/9/2017.
 */

public class CustomListAdapter extends BaseAdapter
{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Newsfeeds> movieItems;
    public CustomListAdapter(MainActivity mainActivity, List<Newsfeeds> newsList) {
        this.activity = mainActivity;
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.movieItems = newsList;
        Log.e("msg",""+movieItems.size());
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
        Glide.with(activity).load(detailss.getImg_url())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView content;
        ImageView imageView;
    }
}
