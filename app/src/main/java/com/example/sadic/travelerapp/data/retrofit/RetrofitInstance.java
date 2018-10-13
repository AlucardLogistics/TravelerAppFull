package com.example.sadic.travelerapp.data.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    final static String BASE_URL_TRAVELER = "http://rjtmobile.com/aamir/otr/android-app/";
    //http://api.openweathermap.org/data/2.5/forecast?lat=41.914196&lon=-88.308685&units=imperial&cnt=40&appid=bb72d2c2b6850337b36813263b0d37ee
    final static String BASE_URL_WEATHER = "http://api.openweathermap.org/data/2.5/";

    static Retrofit retrofit;


    //http://rjtmobile.com/aamir/otr/android-app/
    public static Retrofit getRetrofitInstanceTraveler() {
        if(retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL_TRAVELER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit;
        }

        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL_TRAVELER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    //http://api.openweathermap.org/data/2.5/
    public static Retrofit getRetrofitInstanceWeather() {
        if(retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL_WEATHER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit;
        }

        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL_WEATHER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
