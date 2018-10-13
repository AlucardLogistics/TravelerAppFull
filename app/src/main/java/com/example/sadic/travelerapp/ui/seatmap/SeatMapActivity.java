package com.example.sadic.travelerapp.ui.seatmap;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sadic.travelerapp.R;
import com.example.sadic.travelerapp.adapters.SeatAdapter;
import com.example.sadic.travelerapp.ui.booking.BookingActivity;
import com.example.sadic.travelerapp.ui.seatmap.setup.AbstractItem;
import com.example.sadic.travelerapp.ui.seatmap.setup.CenterItem;
import com.example.sadic.travelerapp.ui.seatmap.setup.EdgeItem;
import com.example.sadic.travelerapp.ui.seatmap.setup.EmptyItem;
import com.example.sadic.travelerapp.ui.seatmap.setup.OnSeatSelected;
import com.example.sadic.travelerapp.ui.seatmap.setup.ReservedItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SeatMapActivity extends AppCompatActivity implements OnSeatSelected, IViewSeatMap{
    private static final String TAG = "SeatMapActivity";

    private static final int COLUMNS = 5;
    private TextView tvSeatsBooked;
    IPresenterSeatMap presenterSeatMap;
    RecyclerView rvSeatMap;
    SeatAdapter myAdapter;
    List<AbstractItem> seats = new ArrayList<>();
    HashSet<Integer> seatNumbers = new HashSet<>();
    int j = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_map);

        init();

        presenterSeatMap = new PresenterSeatMap(this);
        presenterSeatMap.getActivityData();


        GridLayoutManager manager = new GridLayoutManager(this, COLUMNS);
        rvSeatMap.setLayoutManager(manager);
        myAdapter = new SeatAdapter(this, seats);

        rvSeatMap.setAdapter(myAdapter);

    }

    void init() {
        tvSeatsBooked = findViewById(R.id.tvSeatsBooked);
        rvSeatMap = findViewById(R.id.rvSeatMap);

        int inx = 1;
        for(int i = 0; i < 59; i++) {
            if(i % COLUMNS == 0 || i % COLUMNS == 4) {
                seats.add(new EdgeItem(String.valueOf(i), inx));
                inx++;
            } else  if(i % COLUMNS == 1 || i % COLUMNS == 3) {
                seats.add(new CenterItem(String.valueOf(i), inx));
                inx++;
            } else {
                seats.add(new EmptyItem(String.valueOf(i), inx));
            }
        }

        for(AbstractItem seat : seats){
            if(seat.getType() != 2) {
                seat.setLabel("S" + String.valueOf(j));
                j++;
            }
            if(seat.getType() == 2) {
                seat.setLabel("mid row");
            }

            Log.d(TAG, "onCreate: seat: " + seat.getLabel());
        }
    }

    @Override
    public void onSeatSelected(final int count) {
        final int seatsBooked = count;
        if(count == 0) {
            tvSeatsBooked.setText("Book your seats.");
        } else if(count == 1) {
            tvSeatsBooked.setText("Book " + count + " seat.");
        } else {
            tvSeatsBooked.setText("Book " + count + " seats.");
        }

        tvSeatsBooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count != 0) {

                    String result = "";
                    List<Integer> seatsLabel = new ArrayList<>();
                    seatsLabel.addAll(seatNumbers);
                    for(int i = 0; i < seatNumbers.size(); i++) {
                        result = result + String.valueOf(seatsLabel.get(i)) + ",";
                    }
                    Log.d(TAG, "onClick: result: " + result);

                    Intent bookIntent = new Intent(SeatMapActivity.this, BookingActivity.class);
                    bookIntent.putExtra("seatsBooked", String.valueOf(seatsBooked));
                    bookIntent.putExtra("seatsLabels", result);
                    startActivity(bookIntent);
                } else if(count == 0) {
                    Toast.makeText(SeatMapActivity.this, "You have not selected any seats.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void seatPosition(int position) {

        Log.d(TAG, "seatPosition: position selected: " + position);
        seatNumbers.add(position);

        Log.d(TAG, "seatPosition: " +seatNumbers.toString());

    }

    @Override
    public void busSeatReservations(String reservationPattern) {
        Log.d(TAG, "busSeatReservations: reservationPattern: " + reservationPattern);
        int k = 0;
        for(AbstractItem seat : seats) {
            if (seat.getType() != 2 && k < 48) {
                seat.setReserved("S" + String.valueOf(reservationPattern.charAt(k)));
                k++;
                //applyReservedSeats();
            }
            if (seat.getType() == 2) {
                seat.setReserved("mid row");
            }

            Log.d(TAG, "onCreate: seatReservations: " + seat.getReserved());
        }
        applyReservedSeats();

    }

    private void applyReservedSeats() {
        Log.d(TAG, "applyReservedSeats: started");
        for(AbstractItem seat : seats) {
            if (seat.getReserved().equals("S1")) {
                int seatno = seat.getSeatNo();
                int position = seats.indexOf(seat);
                Log.d(TAG, "onCreate: S1 is reserved");
                Log.d(TAG, "onCreate: S1 position: " + position);
                seats.set(position, new ReservedItem(String.valueOf(position), seatno));
                myAdapter.notifyDataSetChanged();
            }
        }
    }

    public void clickHandler(View view) {

    }
}
