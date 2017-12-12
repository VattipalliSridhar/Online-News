package com.sridhar.onlinenewsfeed.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.sridhar.onlinenewsfeed.R;
import com.sridhar.onlinenewsfeed.adater.CustomListAdapter;
import com.sridhar.onlinenewsfeed.models.Newsfeeds;
import com.sridhar.onlinenewsfeed.singletonclasses.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private String url = null;
    private ProgressDialog pDialog;
    private List<Newsfeeds> newsList = new ArrayList<Newsfeeds>();
    private ListView listView;
    private CustomListAdapter adapter;
    private Context context;
    ImageRequest imageRequest;

    private JsonArrayRequest jsonArrayRequest;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();


        requestQueue = VolleySingleton.getVolleySingletonInstance().getRequestQueue();
        listView = (ListView) findViewById(R.id.list);
        context = this;

        url = "https://sites.google.com/site/myapps4748/android/abc.json";
        jsonArrayRequest = getJsonArrayRequest();
        requestQueue.add(jsonArrayRequest);


    }

    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.all_news:

                        newsList.clear();
                        url = "https://sites.google.com/site/myapps4748/android/abc.json";
                        jsonArrayRequest = getJsonArrayRequest();
                        requestQueue.add(jsonArrayRequest);

                        drawerLayout.closeDrawers();
                        break;
                    case R.id.world_news:


                        newsList.clear();
                        url = "https://sites.google.com/site/onlinenews008/onlinenews/world.json";
                        jsonArrayRequest = getJsonArrayRequest();
                        requestQueue.add(jsonArrayRequest);

                        drawerLayout.closeDrawers();
                        break;
                    case R.id.national_news:

                        newsList.clear();
                        url = "https://sites.google.com/site/onlinenews008/onlinenews/national.json";
                        jsonArrayRequest = getJsonArrayRequest();
                        requestQueue.add(jsonArrayRequest);

                        drawerLayout.closeDrawers();
                        break;
                    case R.id.ap_news:

                        newsList.clear();
                        url = "https://sites.google.com/site/onlinenews008/onlinenews/ap_new.json";
                        jsonArrayRequest = getJsonArrayRequest();
                        requestQueue.add(jsonArrayRequest);

                        drawerLayout.closeDrawers();
                        break;
                    case R.id.tg_news:

                        newsList.clear();
                        url = "https://sites.google.com/site/onlinenews008/onlinenews/tg_news.json";
                        jsonArrayRequest = getJsonArrayRequest();
                        requestQueue.add(jsonArrayRequest);

                        drawerLayout.closeDrawers();
                        break;
                    case R.id.sports_news:

                        newsList.clear();
                        url = "https://sites.google.com/site/onlinenews008/onlinenews/sports_news.json";
                        jsonArrayRequest = getJsonArrayRequest();
                        requestQueue.add(jsonArrayRequest);

                        drawerLayout.closeDrawers();
                        break;
                    case R.id.cinema_news:

                        newsList.clear();
                        url = "https://sites.google.com/site/onlinenews008/onlinenews/cinema_news.json";
                        jsonArrayRequest = getJsonArrayRequest();
                        requestQueue.add(jsonArrayRequest);

                        drawerLayout.closeDrawers();
                        break;
                    case R.id.special_quo:

                        newsList.clear();
                        url = "https://sites.google.com/site/onlinenews008/onlinenews/special_quo.json";
                        jsonArrayRequest = getJsonArrayRequest();
                        requestQueue.add(jsonArrayRequest);

                        drawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView) header.findViewById(R.id.app_tv);
        tv_email.setText("" + getResources().getString(R.string.app_name));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private JsonArrayRequest getJsonArrayRequest() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show();
                        try {
                            //jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject obj = (JSONObject) response
                                        .get(i);
                                final Newsfeeds newsfeeds = new Newsfeeds();

                                String s = obj.getString("title");
                                newsfeeds.setTitle(s);


                                String s1 = obj.getString("content");
                                newsfeeds.setContent_text(s1);

                                String s2 = obj.getString("img_url");
                                newsfeeds.setImg_url(s2);

                                String s3 = obj.getString("time");
                                newsfeeds.setTime(s3);


                                newsList.add(0, newsfeeds);


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        pDialog.hide();
                        //Collections.shuffle(newsList);
                        Log.e("msg", "" + newsList.size());
                        adapter = new CustomListAdapter(MainActivity.this, newsList);
                        listView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleVolleyError(error);
                    }
                }
        );
        return jsonArrayRequest;
    }


    private void handleVolleyError(VolleyError error) {
        if (error instanceof AuthFailureError || error instanceof TimeoutError) {
            Toast.makeText(context, "AuthFailureError/TimeoutError", Toast.LENGTH_LONG).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(context, "NoConnectionError", Toast.LENGTH_LONG).show();
        } else if (error instanceof NetworkError) {
            Toast.makeText(context, "NetworkError", Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(context, "ServerError", Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(context, "ParseError", Toast.LENGTH_LONG).show();
        }
    }


}
