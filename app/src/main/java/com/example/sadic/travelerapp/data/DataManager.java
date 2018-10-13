package com.example.sadic.travelerapp.data;

import android.content.Context;

import com.example.sadic.travelerapp.data.network.INetworkHelper;
import com.example.sadic.travelerapp.data.network.NetworkHelper;

public class DataManager implements IDataManager {

    INetworkHelper networkHelper;
    Context context;

    public DataManager(Context context) {
        this.networkHelper = new NetworkHelper(context);
        this.context = context;
    }

    @Override
    public void getCities(OnResponseCitiesListener citiesListener) {
        networkHelper.getCities(citiesListener);
    }

    @Override
    public void getRoute(OnResponseRouteListener routeListener) {
        networkHelper.getRoute(routeListener);
    }

    @Override
    public void getBusDetails(OnResponseBusDetailsListener busDetailsListener) {
        networkHelper.getBusDetails(busDetailsListener);
    }

    @Override
    public void getSeatsReservations(OnResponseReservedSeatsListener responseReservedSeatsListener) {
        networkHelper.getSeatsReservations(responseReservedSeatsListener);
    }

    @Override
    public void getCouponList(OnResponseCuponListener cuponListener) {
        networkHelper.getCouponList(cuponListener);
    }
}
