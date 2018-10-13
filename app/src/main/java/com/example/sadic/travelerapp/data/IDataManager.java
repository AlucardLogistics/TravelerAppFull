package com.example.sadic.travelerapp.data;

import com.example.sadic.travelerapp.data.model.BusInformationItem;
import com.example.sadic.travelerapp.data.model.CityItem;
import com.example.sadic.travelerapp.data.model.RouteItem;
import com.example.sadic.travelerapp.data.network.INetworkHelper;
import com.example.sadic.travelerapp.data.network.model.CouponsItem;
import com.example.sadic.travelerapp.data.network.model.SeatinformationItem;

import java.util.List;

public interface IDataManager extends INetworkHelper {

    interface OnResponseCitiesListener {
        void getCities(List<CityItem> cityItemList);
    }

    interface OnResponseRouteListener {
        void getRoutes(List<RouteItem> routeItemList);
    }

    interface  OnResponseBusDetailsListener {
        void getBusDetails(List<BusInformationItem> busInformationItemList);
    }

    public interface OnResponseReservedSeatsListener {
        void getReservedSeats(String reservationPattern);
    }

    public interface OnResponseCuponListener {
        void getCupons(List<CouponsItem> couponsItemList);
    }



}
