package com.example.sadic.travelerapp.data.retrofit;

import com.example.sadic.travelerapp.data.model.BusInformation;
import com.example.sadic.travelerapp.data.model.City;
import com.example.sadic.travelerapp.data.model.Route;
import com.example.sadic.travelerapp.data.model.weather.Weather;
import com.example.sadic.travelerapp.data.network.model.Cupon;
import com.example.sadic.travelerapp.data.network.model.Seat;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("city.php")
    public Call<City>getCityList();


    //String temp =  "http://rjtmobile.com/aamir/otr/android-app/routeinfo.php?route-startpoint-latitude={startlatitude}&route-startpoint-longitude={startlongitude}&route-endpoint-latitude={endlatitude}&route-endpoint-longiude={endlogitude}";
    @GET("routeinfo.php")
    public Call<Route>getRouteList(
            @Query("route-startpoint-latitude") String startLatitude,
            @Query("route-startpoint-longitude") String startLongitude,
            @Query ("route-endpoint-latitude")  String endLatitude,
            @Query("route-endpoint-longiude") String endLongitude);

    @GET("businfo.php")
    public Call<BusInformation> getBusInformationList(@Query("routeid") String routeId);

    //http://rjtmobile.com/aamir/otr/android-app/seatinfo.php?busid=102
    @GET("seatinfo.php")
    Call<Seat> getSeats(@Query("busid") String busid);

    //http://rjtmobile.com/aamir/otr/android-app/coupon_list.php?
    @GET("coupon_list.php")
    Call<Cupon> getCoupons();

    //http://api.openweathermap.org/data/2.5/forecast?lat=41.914196&lon=-88.308685&units=imperial&cnt=40&appid=bb72d2c2b6850337b36813263b0d37ee
    @GET("forecast")
    Call<Weather> getWeather(@Query("lat") String latitude,
                             @Query("lon") String longitude,
                             @Query("units") String imperial,
                             @Query("cnt") String count,
                             @Query("appid") String apiKey);
}
