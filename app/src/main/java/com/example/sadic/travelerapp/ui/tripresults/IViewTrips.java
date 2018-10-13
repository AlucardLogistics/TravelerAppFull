package com.example.sadic.travelerapp.ui.tripresults;

import com.example.sadic.travelerapp.data.model.BusInformationItem;

import java.util.List;

public interface IViewTrips {

    void getBusDetails(List<BusInformationItem> busInformationItemList);
}
