package com.example.sadic.travelerapp.ui.search;

import com.example.sadic.travelerapp.data.model.CityItem;
import com.example.sadic.travelerapp.data.model.RouteItem;

import java.util.List;

public interface IViewSearch {

    void setRouteDetails(List<RouteItem> routeItemList);
    void getCityList(List<CityItem> cityItemList);
}
