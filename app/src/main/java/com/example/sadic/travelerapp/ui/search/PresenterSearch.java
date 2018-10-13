package com.example.sadic.travelerapp.ui.search;

import android.content.Context;
import android.view.View;

import com.example.sadic.travelerapp.R;
import com.example.sadic.travelerapp.data.DataManager;
import com.example.sadic.travelerapp.data.IDataManager;
import com.example.sadic.travelerapp.data.model.CityItem;
import com.example.sadic.travelerapp.data.model.RouteItem;

import java.util.List;

public class PresenterSearch implements IPresenterSearch, IDataManager.OnResponseCitiesListener, IDataManager.OnResponseRouteListener {

    IViewSearch iView;
    IDataManager dataManager;
    Context context;

    public PresenterSearch(SearchActivity searchActivity) {
        this.context = searchActivity.getApplicationContext();
        dataManager = new DataManager(context);
        iView = searchActivity;
    }

    @Override
    public void getActivityData() {
        dataManager.getCities(this);
    }

    @Override
    public void onButtonClick(View view) {
                dataManager.getRoute(this);
    }

    @Override
    public void getWather() {

    }

    @Override
    public void getCities(List<CityItem> cityItemList) {
        iView.getCityList(cityItemList);
    }

    @Override
    public void getRoutes(List<RouteItem> routeItemList) {
        iView.setRouteDetails(routeItemList);
    }
}
