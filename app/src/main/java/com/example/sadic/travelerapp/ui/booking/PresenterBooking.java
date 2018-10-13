package com.example.sadic.travelerapp.ui.booking;

import android.content.Context;
import android.view.View;

import com.example.sadic.travelerapp.data.DataManager;
import com.example.sadic.travelerapp.data.IDataManager;
import com.example.sadic.travelerapp.data.network.model.CouponsItem;

import java.util.List;

public class PresenterBooking implements IPresenterBooking, IDataManager.OnResponseCuponListener {

    IViewBooking iView;
    IDataManager dataManager;
    Context context;

    public PresenterBooking(BookingActivity bookingActivity) {
        iView = bookingActivity;
        this.context = bookingActivity.getApplicationContext();
        dataManager = new DataManager(bookingActivity);
    }

    @Override
    public void getActivityDate() {
        dataManager.getCouponList(this);
    }

    @Override
    public void onButtonClickHandler(View view) {

    }

    @Override
    public void getCupons(List<CouponsItem> couponsItemList) {
        iView.showCoupons(couponsItemList);
    }
}
