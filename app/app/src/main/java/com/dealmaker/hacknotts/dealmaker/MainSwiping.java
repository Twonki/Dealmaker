package com.dealmaker.hacknotts.dealmaker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class MainSwiping extends AppCompatActivity {
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private TextView mTextMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_swiping);
        getSupportActionBar().setTitle("Find your match");

        BottomNavigationView navigation =( BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        Intent intent1 = new Intent(MainSwiping.this, MainSwiping.class);
                        startActivity(intent1);
                        return true;
                    case R.id.navigation_dashboard:
                        Intent intent2 = new Intent(MainSwiping.this, MatchesActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.navigation_full_matches:
                        Intent intent4 = new Intent(MainSwiping.this,FullActualMatchActivity.class);
                        startActivity(intent4);
                        return true;
                    case R.id.navigation_notifications:
                        Intent intent3 = new Intent(mContext, SettingsActivity.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        };
        mTextMessage = (TextView) findViewById(R.id.message);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mSwipeView = (SwipePlaceHolderView)findViewById(R.id.swipeView);

        mContext = getApplicationContext();


        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f).setSwipeInMsgLayoutId(R.layout.swipe_right_txt)
                        .setSwipeOutMsgLayoutId(R.layout.swipe_left_txt));

        NetworkStuff net = new NetworkStuff(mContext);

        net.get_matches(Utils.accoutn_id, new networkListener() {
            @Override
            public void onResponseGet(List<Profile> response) {
                for(Profile prf : response) {
                    mSwipeView.addView(new Card(mContext, prf, mSwipeView));
                }
            }
        });



    }

}
