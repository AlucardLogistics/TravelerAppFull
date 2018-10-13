package com.example.sadic.travelerapp.data.network;

import com.example.sadic.travelerapp.data.IDataManager;

public interface INetworkHelper {

    void getCities(IDataManager.OnResponseCitiesListener citiesListener);

    void getRoute(IDataManager.OnResponseRouteListener routeListener);

    void getBusDetails(IDataManager.OnResponseBusDetailsListener busDetailsListener);

    void getSeatsReservations(IDataManager.OnResponseReservedSeatsListener responseReservedSeatsListener);

    void getCouponList(IDataManager.OnResponseCuponListener cuponListener);
}
