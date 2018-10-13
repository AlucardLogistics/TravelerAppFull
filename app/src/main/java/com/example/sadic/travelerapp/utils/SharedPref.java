package com.example.sadic.travelerapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static SharedPreferences mSharedPref;
    public static final String ID = "ID";
    public static final String DISPLAY_NAME = "DISPLAY_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String MOBILE = "MOBILE";
    public static final String PHOTO_URL = "PHOTO_URL";
    public static final String API_KEY = "API_KEY";
    public static final String CITY_DEPARTURE = "CITY_DEPARTURE";
    public static final String LATITUDE_DEPARTURE = "LATITUDE_DEPARTURE";
    public static final String LONGITUDE_DEPARTURE = "LONGITUDE_DEPARTURE";
    public static final String CITY_ARRIVAL = "CITY_ARRIVAL";
    public static final String LATITUDE_ARRIVAL = "LATITUDE_ARRIVAL";
    public static final String LONGITUDE_ARRIVAL = "LONGITUDE_ARRIVAL";
    public static final String ROUTE_ID = "ROUTE_ID";
    public static final String BUS_ID = "BUS_ID";
    public static final String ROUTE_NAME = "ROUTE_NAME";
    public static final String ROUTE_DEPARTURE = "ROUTE_DEPARTURE";
    public static final String ROUTE_ARRIVAL = "ROUTE_ARRIVAL";
    public static final String ROUTE_DURATION = "ROUTE_DURATION";
    public static final String ROUTE_FARE = "ROUTE_FARE";
    public static final String BUS_TYPE = "BUS_TYPE";
    public static final String PAYPAL_CLIENT_ID = "Aen7s30aphUc7rj5jGn0wJhIrJW8JPxJdXJXitHbOBLkyFUKH0uXNagdguTmH4bqgT5C5QOiXFlrhroT";



    private SharedPref()
    {

    }

    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences("mySharedFile", Activity.MODE_PRIVATE);
    }

    public static String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public static boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public static Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).commit();
    }
}
