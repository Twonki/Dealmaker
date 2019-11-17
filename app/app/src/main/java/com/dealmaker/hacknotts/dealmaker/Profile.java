package com.dealmaker.hacknotts.dealmaker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by morgan on 16/11/2019.
 */

public class Profile {
    @Expose
    @SerializedName("firstname")
    private String name;
    @Expose
    @SerializedName("score")
    private double score;
    @Expose
    @SerializedName("account")
    private String account_id;
    @Expose
    @SerializedName("category")
    private Map<String,Double> categories; // stores all the categories with perecentages for each.

    public Profile(String name, double score, String account_id,Map<String,Double> categories){
        this.name=name;
        this.score=score;
        this.account_id=account_id;
        this.categories=categories;
    }
    public String getName(){
        return name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public void setCategories(Map<String, Double> categories) {
        this.categories = categories;
    }

    public Map<String,Double> getCategories(){
        return categories;
    }
}
