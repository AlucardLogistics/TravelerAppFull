package com.example.sadic.travelerapp.data.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.sadic.travelerapp.data.IDataManager;
import com.example.sadic.travelerapp.data.model.BusInformation;
import com.example.sadic.travelerapp.data.model.City;
import com.example.sadic.travelerapp.data.model.CityItem;
import com.example.sadic.travelerapp.data.model.Route;
import com.example.sadic.travelerapp.data.network.model.CouponsItem;
import com.example.sadic.travelerapp.data.network.model.Cupon;
import com.example.sadic.travelerapp.data.network.model.Seat;
import com.example.sadic.travelerapp.data.retrofit.ApiService;
import com.example.sadic.travelerapp.data.retrofit.RetrofitInstance;
import com.example.sadic.travelerapp.utils.SharedPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkHelper implements INetworkHelper {
    private static final String TAG = "NetworkHelper";

    Context context;

    public NetworkHelper(Context context) {
        this.context = context;
    }

    ApiService apiService = RetrofitInstance.getRetrofitInstanceTraveler()
            .create(ApiService.class);

    @Override
    public void getRoute(final IDataManager.OnResponseRouteListener routeListener) {
        SharedPref.init(context);
        String departureLat = SharedPref.read(SharedPref.LATITUDE_DEPARTURE, "");
        String departureLong = SharedPref.read(SharedPref.LONGITUDE_DEPARTURE, "");
        String arrivalLat = SharedPref.read(SharedPref.LATITUDE_ARRIVAL, "");
        String arrivalLong = SharedPref.read(SharedPref.LONGITUDE_ARRIVAL, "");
        Log.d(TAG, "getRoute: ");
        Call<Route> routeCall = apiService.getRouteList(departureLat, departureLong, arrivalLat, arrivalLong);
        routeCall.enqueue(new Callback<Route>() {
            @Override
            public void onResponse(Call<Route> call, Response<Route> response) {
                Log.d(TAG, "onResponse: route: " + response.body().getRoute());
                Log.d(TAG, "onResponse: responseString: " + response.body().getRoute());
                if(response.body().getRoute() != null) {
                    routeListener.getRoutes(response.body().getRoute());
                } else {
                    Toast.makeText(context, "Route not available.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Route> call, Throwable t) {
                Log.d(TAG, "onFailure: routes: " + t.getMessage());
            }
        });
    }

    @Override
    public void getBusDetails(final IDataManager.OnResponseBusDetailsListener busDetailsListener) {
        SharedPref.init(context);
        String routeId = SharedPref.read(SharedPref.ROUTE_ID, "");
        Call<BusInformation> busInformationCall = apiService.getBusInformationList(routeId);
        busInformationCall.enqueue(new Callback<BusInformation>() {
            @Override
            public void onResponse(Call<BusInformation> call, Response<BusInformation> response) {
                Log.d(TAG, "onResponse: busInfo: " + response.body().getBusinformation());
                busDetailsListener.getBusDetails(response.body().getBusinformation());
            }

            @Override
            public void onFailure(Call<BusInformation> call, Throwable t) {
                Log.d(TAG, "onFailure: busInfo: " + t.getMessage());

            }
        });
    }

    @Override
    public void getCities(final IDataManager.OnResponseCitiesListener citiesListener) {
        Call<City> cityCall = apiService.getCityList();
        cityCall.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                Log.d(TAG, "onResponse: CityListL " + response.body().getCity());
                List<CityItem> cityItems = response.body().getCity();
                citiesListener.getCities(cityItems);
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Log.d(TAG, "onFailure: city: " + t.getMessage());

            }
        });
    }

    @Override
    public void getSeatsReservations(final IDataManager.OnResponseReservedSeatsListener responseReservedSeatsListener) {
        SharedPref.init(context);
        String busId = SharedPref.read(SharedPref.BUS_ID, "");
        Log.d(TAG, "getSeatsReservations: **********busID: " + busId);
        Call<Seat> seatCall = apiService.getSeats(busId);
        seatCall.enqueue(new Callback<Seat>() {
            @Override
            public void onResponse(Call<Seat> call, Response<Seat> response) {
                Log.d(TAG, "onResponse: seats: " + response.body().getSeatinformation());
                //getting seats pattern
                String temp = response.body().toString();
                String result ="";
                for (int i =60; i< temp.length();i++)
                {
                    if (temp.charAt(i)== '}')
                        break;
                    else
                    if((temp.charAt(i) == '1' && temp.charAt(i+1) =='\'') ||
                            (temp.charAt(i) == '0' && temp.charAt(i+1) =='\''))
                    {
                       result += temp.charAt(i);
                    }

                }
                Log.d(TAG, "onResponse: Result = " + result+ " length: " + result.length());

                //List<SeatinformationItem> seatinformationItemList = response.body().getSeatinformation();
                if(response != null) {
                    responseReservedSeatsListener.getReservedSeats(result);
                }
            }

            @Override
            public void onFailure(Call<Seat> call, Throwable t) {
                Log.d(TAG, "onFailure: FAIL: " + t.getMessage());

            }
        });
    }

    @Override
    public void getCouponList(final IDataManager.OnResponseCuponListener cuponListener) {
        Call<Cupon> cuponCall = apiService.getCoupons();
        cuponCall.enqueue(new Callback<Cupon>() {
            @Override
            public void onResponse(Call<Cupon> call, Response<Cupon> response) {
                Log.d(TAG, "onResponse: coupons: " + response.body().getCoupons());
                List<CouponsItem> couponsItemList = response.body().getCoupons();
                cuponListener.getCupons(couponsItemList);
            }

            @Override
            public void onFailure(Call<Cupon> call, Throwable t) {
                Log.d(TAG, "onFailure: cupon-FAIL: " + t.getMessage());

            }
        });
    }
}
