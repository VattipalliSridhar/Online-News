package com.sridhar.onlinenewsfeed.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private RequestQueue requestQueue;
    private String url = "https://sites.google.com/site/myapps4748/android/abc.json";
    private ProgressDialog pDialog;
    private List<Newsfeeds> newsList = new ArrayList<Newsfeeds>();
    private ListView listView;
    private CustomListAdapter adapter;
    private Context context;
    ImageRequest imageRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = VolleySingleton.getVolleySingletonInstance().getRequestQueue();

        listView=(ListView)findViewById(R.id.list);


        context=this;



        JsonArrayRequest jsonArrayRequest = getJsonArrayRequest();

        requestQueue.add(jsonArrayRequest);





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
                        try
                        {
                            //jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject obj = (JSONObject) response
                                        .get(i);
                                final Newsfeeds newsfeeds=new Newsfeeds();

                                String s=obj.getString("title");
                                newsfeeds.setTitle(s);


                                String s1=obj.getString("content");
                                newsfeeds.setContent_text(s1);

                                String s2=obj.getString("img_url");
                                newsfeeds.setImg_url(s2);

                                /*ImageRequest imageRequest = new ImageRequest(
                                        obj.getString("img_url"),
                                        new Response.Listener<Bitmap>() {
                                            @Override
                                            public void onResponse(Bitmap response) {

                                                newsfeeds.setBitmap(response);

                                            }
                                        },
                                        200, 200,
                                        ImageView.ScaleType.CENTER_CROP,
                                        Bitmap.Config.ARGB_8888,
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                // handleVolleyError(error);
                                            }
                                        });
                                requestQueue.add(imageRequest);*/

                                newsList.add(newsfeeds);


                            }


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        pDialog.hide();
                        Log.e("msg",""+newsList.size());
                        adapter=new CustomListAdapter(MainActivity.this,newsList);
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




    private void handleVolleyError(VolleyError error)
    {
        if (error instanceof AuthFailureError || error instanceof TimeoutError)
        {
            Toast.makeText(context,"AuthFailureError/TimeoutError",Toast.LENGTH_LONG).show();
        }
        else if (error instanceof NoConnectionError)
        {
            Toast.makeText(context,"NoConnectionError",Toast.LENGTH_LONG).show();
        }
        else if (error instanceof NetworkError)
        {
            Toast.makeText(context,"NetworkError",Toast.LENGTH_LONG).show();
        }
        else if (error instanceof ServerError)
        {
            Toast.makeText(context,"ServerError",Toast.LENGTH_LONG).show();
        }
        else if (error instanceof ParseError)
        {
            Toast.makeText(context,"ParseError",Toast.LENGTH_LONG).show();
        }
    }



}
