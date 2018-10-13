package com.example.sadic.travelerapp.ui.tripresults;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sadic.travelerapp.R;
import com.example.sadic.travelerapp.adapters.TripsAdapter;
import com.example.sadic.travelerapp.data.model.BusInfo;
import com.example.sadic.travelerapp.data.model.BusInformationItem;
import com.example.sadic.travelerapp.utils.SharedPref;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class TripResultsActivity extends AppCompatActivity implements IViewTrips, OnMapReadyCallback {
    private static final String TAG = "TripResultsActivity";

    RecyclerView rvTrips;
    TripsAdapter adapter;
    IPresenterTrips presenterTrips;
    List<BusInfo> busInfoList = new ArrayList<>();
    BusInfo busInfo;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        SharedPref.init(this);

        init();

        presenterTrips = new PresenterTrips(this);
        presenterTrips.setActivityData();


    }

    void init() {
        rvTrips = findViewById(R.id.rvTrips);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvTrips.setLayoutManager(manager);
        rvTrips.setItemAnimator(new DefaultItemAnimator());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void getBusDetails(List<BusInformationItem> busInformationItemList) {
        Log.d(TAG, "getBusDetails: " + busInformationItemList.toString());


        for(BusInformationItem b : busInformationItemList) {
            busInfo = new BusInfo(b.getBusid(), b.getFare(), b.getBusregistrationno(), b.getJournyduration(),
                    b.getBoardingtime(), b.getBustype(), b.getBusdeparturetime(), b.getDropingtime());
            busInfoList.add(busInfo);
        }
        SharedPref.write(SharedPref.BUS_ID, busInfoList.get(0).getBusid());
        SharedPref.write(SharedPref.ROUTE_DEPARTURE, busInfoList.get(0).getBusdeparturetime());
        SharedPref.write(SharedPref.ROUTE_ARRIVAL, busInfoList.get(0).getDropingtime());
        SharedPref.write(SharedPref.ROUTE_DURATION, busInfoList.get(0).getJournyduration());
        SharedPref.write(SharedPref.ROUTE_FARE, busInfoList.get(0).getFare());
        SharedPref.write(SharedPref.BUS_TYPE, busInfoList.get(0).getBustype());

        adapter = new TripsAdapter(this, busInfoList);
        rvTrips.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.retro_map_style));

        String depLat = SharedPref.read(SharedPref.LATITUDE_DEPARTURE, "");
        String depLon = SharedPref.read(SharedPref.LONGITUDE_DEPARTURE, "");
        String arrLat = SharedPref.read(SharedPref.LATITUDE_ARRIVAL, "");
        String arrLon = SharedPref.read(SharedPref.LONGITUDE_ARRIVAL, "");
        Log.d(TAG, "onMapReady: depLat depLon " + depLat + " " + depLon);
        Log.d(TAG, "onMapReady: arrLat arrLon " + arrLat + " " + arrLon);
        // Add a marker in Sydney and move the camera
        LatLng departure = new LatLng(Double.parseDouble(depLat), Double.parseDouble(depLon));
        LatLng arrival = new LatLng(Double.parseDouble(arrLat), Double.parseDouble(arrLon));
        mMap.addMarker(new MarkerOptions().position(departure).title("Marker in Departure"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(departure));
        mMap.addMarker(new MarkerOptions().position(arrival).title("Marker in Arrival"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(arrival));

        Double midLat = (Double.parseDouble(depLat) + Double.parseDouble(arrLat)) /2;
        Double midLog = (Double.parseDouble(depLon) + Double.parseDouble(arrLon)) /2;
        LatLng midPoint = new LatLng(midLat, midLog);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(midPoint,4));
    }
}
