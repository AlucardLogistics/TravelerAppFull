package com.example.sadic.travelerapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sadic.travelerapp.R;
import com.example.sadic.travelerapp.data.model.BusInfo;
import com.example.sadic.travelerapp.data.model.BusInformationItem;
import com.example.sadic.travelerapp.ui.seatmap.SeatMapActivity;
import com.example.sadic.travelerapp.utils.SharedPref;

import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.TripViewHolder> {
    private static final String TAG = "TripsAdapter";

    Context context;
    List<BusInfo> busInfoList;
    //List<BusInformationItem> busInformationItems;

    public TripsAdapter(Context context, List<BusInfo> busInfoList) {
        this.context = context;
        this.busInfoList = busInfoList;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_trip_details, parent, false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SeatMapActivity.class);
                context.startActivity(i);
            }
        });

        return new TripViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        BusInfo busInfo = busInfoList.get(position);
        SharedPref.init(context);
        String routeName = SharedPref.read(SharedPref.ROUTE_NAME, "");
        //BusInformationItem busDetails = busInformationItems.get(position);

        holder.tvRoute.setText(routeName);
        holder.tvBusType.setText("Bus Type: " + busInfo.getBustype());
        holder.tvBusFare.setText("Fare: " + busInfo.getFare() + " $");
        holder.tvBusDeparture.setText("Dep: " + busInfo.getBusdeparturetime());
        holder.tvBusTravelTime.setText("Time: " + busInfo.getJournyduration());
        holder.tvBusArrival.setText("Arr: " + busInfo.getDropingtime());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + busInfoList.size());
        return busInfoList.size();
    }

    public class TripViewHolder extends RecyclerView.ViewHolder {

        TextView tvRoute, tvBusType, tvBusFare, tvBusDeparture, tvBusTravelTime, tvBusArrival;

        public TripViewHolder(View itemView) {
            super(itemView);

            tvRoute = itemView.findViewById(R.id.tvBusRoute);
            tvBusType = itemView.findViewById(R.id.tvBusType);
            tvBusFare = itemView.findViewById(R.id.tvBusFare);
            tvBusDeparture = itemView.findViewById(R.id.tvBusDeparture);
            tvBusTravelTime = itemView.findViewById(R.id.tvBusTravelTime);
            tvBusArrival = itemView.findViewById(R.id.tvBusArrival);
        }
    }
}
