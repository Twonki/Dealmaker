package com.dealmaker.hacknotts.dealmaker;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by morgan on 16/11/2019.
 */

public class Utils {
    public static String accoutn_id="10015988";

    public static  void sendToSettingsSharedPrefs(Context context, UserPreferences up){
        SharedPreferences mPrefs = context.getSharedPreferences("com.dealmaker.hacknotts",MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(up); // myObject - instance of MyObject
        prefsEditor.putString("settings_obj", json);
        prefsEditor.commit();
    }

    public static UserPreferences getSettingsFromSharedPrefs(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("com.dealmaker.hacknotts",MODE_PRIVATE);
        if(mPrefs.contains("settings_obj")) {
            Gson gson = new Gson();
            String json = mPrefs.getString("settings_obj", "");
            UserPreferences obj = gson.fromJson(json, UserPreferences.class);

            return obj;
        }else{
            UserPreferences prefs = new UserPreferences();
            return prefs;
        }
    }

    /**
     * Takes two dictionaries and returns the most similar value
     */

}
