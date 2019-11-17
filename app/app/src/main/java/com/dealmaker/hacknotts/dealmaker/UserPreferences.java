package com.dealmaker.hacknotts.dealmaker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by morgan on 16/11/2019.
 */

public class UserPreferences {
    private String name="";
    private int age=-1;
    private String gender="";
    private double latitude=2;
    private double longitude=2;
    private String account_id="1";

    public UserPreferences(String name, int age, String gender, double latitude, double longitude, String account_id) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.latitude = latitude;
        this.longitude = longitude;
        this.account_id=account_id;
    }
    public UserPreferences(){

    }
    public boolean isValidPrefs(){
        return (!name.equals("")&&age>=18&&age<=120&&!gender.equals("")&&latitude!=-1&&longitude!=-1&&!account_id.equals(""));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }
}
