package com.dealmaker.hacknotts.dealmaker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by morgan on 16/11/2019.
 */

public class NetworkStuff {
    private RequestQueue requestQueue;
    private Context mContext;


    /**
     * Set context of this class and set request queue
     * @param context
     */
    public NetworkStuff(Context context){
        this.mContext=context;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void get_matches(String userid,final networkListener ntl){
        // Instantiate the RequestQueue.

        String url = "http://applis.me:8080/dealmakerapi-0.0.1-SNAPSHOT/matches/"+userid;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println(response);

                        Gson gson = new Gson();


                        Type listType = new TypeToken<List<Profile>>(){}.getType();

                        // 1. JSON file to Java object
                        List<Profile> object =  gson.fromJson(response, listType);

                        ntl.onResponseGet(object);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR!");

            }
        });

// Add the request to the RequestQueue.
        requestQueue.add(stringRequest);

    }
    public void getInfo(String account_id, final networkListener nlt){
        String url = "http://applis.me:8080/dealmakerapi-0.0.1-SNAPSHOT/info/"+account_id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println(response);

                        Gson gson = new Gson();

                        Type listType = new TypeToken<Profile>(){}.getType();

                        // 1. JSON file to Java object
                        Profile rep = gson.fromJson(response, listType);
                        List<Profile> object = new ArrayList<>();
                        object.add(rep);
                        System.out.println("Running get info");
                        nlt.onResponseGet(object);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR!");

            }
        });

        requestQueue.add(stringRequest);
    }
    /**
     * returns people's id's who liked you back
     * @param userId this users id
     */
    public void get_mutual_deals(String userId, final networkStringInterface ntl){
        String url = "http://applis.me:8080/dealmakerapi-0.0.1-SNAPSHOT/deals/"+userId;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("response from mutual"+response);

                        Gson gson = new Gson();

                        Type listType = new TypeToken<String[]>(){}.getType();

                        // 1. JSON file to Java object
                        String[] object =  gson.fromJson(response, listType);

                        ntl.onStringUpdate(object);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR!");

            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);

    }

    /**
     *
     * @param isRight Is this a right swipe?
     * @param this_user This devices user id
     * @param other_user
     */
    public void sendSwipe(boolean isRight, String this_user, final String other_user) {
        String url = "http://applis.me:8080/dealmakerapi-0.0.1-SNAPSHOT/deals/"+this_user+"?acc="+other_user+"&like="+isRight;

        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.i("Error.Response","Wrong");
                    }
                }
        ) {

        };
        requestQueue.add(putRequest);

    }


    public Profile getMyProfile() {
        Map<String,Double> mp = new HashMap<>();
        mp.put("Health and fittness",0.4);
        mp.put("Gaming",0.2);
        mp.put("Food and housing",0.4);
        return new Profile("morgan", 0.1,"1",mp);
    }
}
