package com.dealmaker.hacknotts.dealmaker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    Context mContext;
    UserPreferences userPrefs;
    private SwipePlaceHolderView mSwipeView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            System.out.println("Clicked somin");
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    updatePrefsObject(userPrefs);
                    if(userPrefs.isValidPrefs()) {
                        Utils.sendToSettingsSharedPrefs(mContext,userPrefs);
                        Intent intent1 = new Intent(SettingsActivity.this, MainSwiping.class);
                        startActivity(intent1);
                    }else{
                        Toast.makeText(mContext,"Please check that you've entered your settings properly!",Toast.LENGTH_LONG).show();
                    }
                    return true;
                case R.id.navigation_dashboard:
                    updatePrefsObject(userPrefs);
                    if(userPrefs.isValidPrefs()) {
                        Utils.sendToSettingsSharedPrefs(mContext,userPrefs);
                        Intent intent2 = new Intent(SettingsActivity.this, MatchesActivity.class);
                        startActivity(intent2);
                    }else{
                        Toast.makeText(mContext,"Please check that you've entered your settings properly!",Toast.LENGTH_LONG).show();
                    }
                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };

    protected void updatePrefsObject(UserPreferences userPrefs){
        String s = (String) ((TextView)findViewById(R.id.txtName)).getText().toString();
        userPrefs.setName(s);
        s = (String) ((TextView)findViewById(R.id.txtAge)).getText().toString();
        System.out.println("Age is : "+s);
        System.out.println(Integer.parseInt(s));
        userPrefs.setAge(Integer.parseInt(s));
        s = (String) ((TextView)findViewById(R.id.txtGender)).getText().toString();
        userPrefs.setGender(s);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Your Profile");

        mContext = getApplicationContext();

        BottomNavigationView navigation =( BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        updatePrefsObject(userPrefs);
                        if(userPrefs.isValidPrefs()) {
                            Utils.sendToSettingsSharedPrefs(mContext,userPrefs);
                            Intent intent1 = new Intent(SettingsActivity.this, MainSwiping.class);
                            startActivity(intent1);
                        }else{
                            Toast.makeText(mContext,"Please check that you've entered your settings properly!",Toast.LENGTH_LONG).show();
                        }
                        return true;
                    case R.id.navigation_dashboard:
                        updatePrefsObject(userPrefs);
                        if(userPrefs.isValidPrefs()) {
                            Utils.sendToSettingsSharedPrefs(mContext,userPrefs);
                            Intent intent2 = new Intent(SettingsActivity.this, MatchesActivity.class);
                            startActivity(intent2);
                        }else{
                            Toast.makeText(mContext,"Please check that you've entered your settings properly!",Toast.LENGTH_LONG).show();
                        }
                        return true;
                    case R.id.navigation_full_matches:
                        updatePrefsObject(userPrefs);
                        if(userPrefs.isValidPrefs()) {
                            Utils.sendToSettingsSharedPrefs(mContext,userPrefs);
                            Intent intent2 = new Intent(SettingsActivity.this, FullActualMatchActivity.class);
                            startActivity(intent2);
                        }else{
                            Toast.makeText(mContext,"Please check that you've entered your settings properly!",Toast.LENGTH_LONG).show();
                        }
                        return true;
                    case R.id.navigation_notifications:
                        break;
                    }
                return true;
            }
        };
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);        userPrefs = Utils.getSettingsFromSharedPrefs(mContext);
        if (userPrefs.isValidPrefs()){
            updateUIForUserPrefs(userPrefs);
        }


        //Deal with preview pie chart
        mSwipeView = (SwipePlaceHolderView)findViewById(R.id.swipeView);

        mContext = getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(5));
        mSwipeView.disableTouchSwipe();
        NetworkStuff net = new NetworkStuff(mContext);
        //findViewById(R.id.txtPieName).setVisibility(View.INVISIBLE);//TODO Make it so the name doesn't show up in the settings view.
        mSwipeView.addView(new Card(mContext, net.getMyProfile(), mSwipeView));


    }


    protected void updateUIForUserPrefs(UserPreferences usp){
        ((TextView)findViewById(R.id.txtAge)).setText(Integer.toString(usp.getAge()), TextView.BufferType.EDITABLE);
        ((TextView)findViewById(R.id.txtName)).setText(usp.getName(), TextView.BufferType.EDITABLE);
        ((TextView)findViewById(R.id.txtGender)).setText(usp.getGender(), TextView.BufferType.EDITABLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
