package com.dealmaker.hacknotts.dealmaker;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by morgan on 16/11/2019.
 */
@Layout(R.layout.card_view)
public class Card {

    @View(R.id.txtPieName)
    public TextView nameAgeTxt;
    @View(R.id.chart)
    public PieChartView pieChartView;

    public Context mContext;
    public SwipePlaceHolderView mSwipeView;
    public Profile mProfile;

    private int[] colorList=new int[]{0xFFFFBF7D,0xffEB8C71,0xFFFF8AA1,0xFFDD72E8,0xFFAE7DFF,0xFFFF999E,0xFFDF6DEB,0xFFFF91E9,0xFFFFE991};

    public Card(Context context, Profile profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;

    }
    public void disableTouchSwipes(boolean doDisable){
        if (doDisable){mSwipeView.disableTouchSwipe();}else{mSwipeView.enableTouchSwipe();}
    }
    @Resolve
    public void onResolved(){
        nameAgeTxt.setText(mProfile.getName());

        Map<String,Double> cats = mProfile.getCategories();
        List<SliceValue> pieData = new ArrayList<>();

        int color_list_idx = 0;
        for(String k : cats.keySet()){
            double value=cats.get(k);
            System.out.println("Setting :"+k+" to "+colorList[color_list_idx]);
            pieData.add(new SliceValue(Math.round(value*100),colorList[color_list_idx]).setLabel(k));
            color_list_idx++;
        }

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartView.setPieChartData(pieChartData);

    }
    //Swipeout = left swipe
    @SwipeOut
    public void onSwipedOut(){
        NetworkStuff net = new NetworkStuff(this.mContext);
        net.sendSwipe(false,Utils.accoutn_id,this.mProfile.getAccount_id());//TODO: CHange make the user ids work.
        Log.d("EVENT", "onSwipedIn");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    public void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }
    //swipe in == right swipe
    @SwipeIn
    public void onSwipeIn(){
        NetworkStuff net = new NetworkStuff(this.mContext);
        net.sendSwipe(true, Utils.accoutn_id, this.mProfile.getAccount_id());
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    public void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    public void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }
}
