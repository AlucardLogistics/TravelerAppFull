package com.example.sadic.travelerapp.ui.seatmap;

import android.content.Context;
import android.view.View;

import com.example.sadic.travelerapp.data.DataManager;
import com.example.sadic.travelerapp.data.IDataManager;
import com.example.sadic.travelerapp.data.network.model.SeatinformationItem;

import java.util.List;

public class PresenterSeatMap implements IPresenterSeatMap, IDataManager.OnResponseReservedSeatsListener {

    IViewSeatMap iView;
    Context context;
    IDataManager dataManager;

    public PresenterSeatMap(SeatMapActivity seatMapActivity) {
        iView = seatMapActivity;
        this.context = seatMapActivity.getApplicationContext();
        dataManager = new DataManager(seatMapActivity);
    }

    @Override
    public void getActivityData() {
        dataManager.getSeatsReservations(this);
    }

    @Override
    public void onButtonClickEvent(View view) {

    }

    @Override
    public void getReservedSeats(String reservationPattern) {
        iView.busSeatReservations(reservationPattern);
    }
}
