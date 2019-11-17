package com.dealmaker.hacknotts.dealmaker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class FullActualMatchActivity extends AppCompatActivity {
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext=getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_actual_match);
        getSupportActionBar().setTitle("Completed Deals");

        BottomNavigationView navigation =( BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        Intent intent1 = new Intent(FullActualMatchActivity.this, MainSwiping.class);
                        startActivity(intent1);
                        return true;
                    case R.id.navigation_dashboard:
                        Intent intent2 = new Intent(FullActualMatchActivity.this, MatchesActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.navigation_full_matches:
                        Intent intent4 = new Intent(FullActualMatchActivity.this,FullActualMatchActivity.class);
                        startActivity(intent4);
                        return true;
                    case R.id.navigation_notifications:
                        Intent intent3 = new Intent(FullActualMatchActivity.this, SettingsActivity.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        };
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
}
