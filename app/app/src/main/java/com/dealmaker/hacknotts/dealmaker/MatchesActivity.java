package com.dealmaker.hacknotts.dealmaker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchesActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        Intent intent1 = new Intent(MatchesActivity.this, MainSwiping.class);
                        startActivity(intent1);
                        return true;
                    case R.id.navigation_dashboard:
                        Intent intent2 = new Intent(MatchesActivity.this, MatchesActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.navigation_full_matches:
                        Intent intent4 = new Intent(MatchesActivity.this,FullActualMatchActivity.class);
                        startActivity(intent4);
                        return true;
                    case R.id.navigation_notifications:
                        Intent intent3 = new Intent(MatchesActivity.this, SettingsActivity.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        };
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mContext = getApplicationContext();
        getSupportActionBar().setTitle("Your Deals");

        final SwipePlaceHolderView swipePlaceHolderView =(SwipePlaceHolderView) findViewById(R.id.swipeView_list);

        swipePlaceHolderView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f).setSwipeInMsgLayoutId(R.layout.swipe_right_txt)
                        .setSwipeOutMsgLayoutId(R.layout.swipe_left_txt));

        final NetworkStuff net = new NetworkStuff(mContext);

        net.get_mutual_deals(Utils.accoutn_id, new networkStringInterface() {
            @Override
            public void onStringUpdate(String[] response) {

                    for(String id : response) {

                        net.getInfo(id, new networkListener() {

                            @Override
                            public void onResponseGet(List<Profile> profiles) {
                                if(profiles.size()>0) {
                                    System.out.println("profile" + profiles.get(0));
                                    swipePlaceHolderView.addView(new CardForList(mContext, profiles.get(0), swipePlaceHolderView));
                                }
                            }
                        });

                    }
                }

            }
        );

        // Control of buttons
        final EditText txtMePay = findViewById(R.id.txtMePay);
        final EditText txtYouPay = findViewById(R.id.txtTheyPay);
        txtMePay.setEnabled(false);
        txtMePay.setClickable(false);
        txtYouPay.setEnabled(false);
        txtYouPay.setClickable(false);
        findViewById(R.id.incMePay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int c = Integer.parseInt(txtMePay.getText().toString());
               c+=5;
               txtMePay.setText(Integer.toString(c));
            }
        });

        findViewById(R.id.decMePay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int c = Integer.parseInt(txtMePay.getText().toString());
                c-=5;
                txtMePay.setText(Integer.toString(c));
            }
        });
        findViewById(R.id.decYouPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int c = Integer.parseInt(txtYouPay.getText().toString());
                c-=5;
                txtYouPay.setText(Integer.toString(c));
            }
        });
        findViewById(R.id.incYouPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int c = Integer.parseInt(txtYouPay.getText().toString());
                c+=5;
                txtYouPay.setText(Integer.toString(c));
            }
        });

    }
}
