package com.example.sadic.travelerapp.ui.search;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.sadic.travelerapp.R;

public class CalendarFragment extends Fragment {
    private static final String TAG = "CalendarFragment";

    public interface OnDataPass {
        void onDataPass(String data);
    }

    CalendarView calendarView;
    Fragment me = this;

    OnDataPass datePasser;
    String curDate = null;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        datePasser = (OnDataPass) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.fragment_calendar, container, false);

            calendarView = v.findViewById(R.id.calendarView);
            calendarView.setMinDate(System.currentTimeMillis() - 1000);
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                    String day;
                    if(dayOfMonth < 10) {
                        day = "0" + String.valueOf(dayOfMonth);
                    } else {
                        day = String.valueOf(dayOfMonth);
                    }
                   curDate = String.valueOf(year) + "-"
                           + String.valueOf(month+1) + "-"
                           + day;
                    datePasser.onDataPass(curDate);


                    getActivity().getSupportFragmentManager()
                            .beginTransaction().remove(me).commit();
                }
            });


         return v;
    }

    public void passData(String curDate) {

    }
}
