package com.example.sadic.travelerapp.ui.tripresults;

import android.content.Context;

import com.example.sadic.travelerapp.data.DataManager;
import com.example.sadic.travelerapp.data.IDataManager;
import com.example.sadic.travelerapp.data.model.BusInformationItem;

import java.util.List;

public class PresenterTrips implements IPresenterTrips, IDataManager.OnResponseBusDetailsListener {

    IViewTrips iView;
    Context context;
    IDataManager dataManager;

    public PresenterTrips(TripResultsActivity tripResultsActivity) {
        this.context = tripResultsActivity.getApplicationContext();
        iView = tripResultsActivity;
        dataManager = new DataManager(context);
    }

    @Override
    public void setActivityData() {
        dataManager.getBusDetails(this);
    }

    @Override
    public void getBusDetails(List<BusInformationItem> busInformationItemList) {
        iView.getBusDetails(busInformationItemList);
    }
}
