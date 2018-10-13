package com.example.sadic.travelerapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sadic.travelerapp.R;
import com.example.sadic.travelerapp.data.model.Guest;
import com.example.sadic.travelerapp.ui.payment.CustomEmailDomainValidator;
import com.github.phajduk.rxvalidator.RxValidationResult;
import com.github.phajduk.rxvalidator.RxValidator;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.GuestViewHolder> {
    private static final String TAG = "GuestAdapter";

    Context context;
    List<Guest> guestList;
    int count;

    public GuestAdapter(Context context, List<Guest> guestList, int count) {
        this.context = context;
        this.guestList = guestList;
        this.count = count;
    }

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_guests, parent, false);

        return new GuestViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull GuestViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: started Getting guests");

        String gEmail = holder.etGuestEmail.getText().toString();
        String gName = holder.etGuestName.getText().toString();
        String gMobile = holder.etGuestMobile.getText().toString();
        Guest guest = new Guest(gEmail, gName, gMobile);
        guestList.add(guest);
        Log.d(TAG, "onBindViewHolder: *********guestList: " + guestList.toString());

        RxValidator.createFor(holder.etGuestEmail)
                .nonEmpty()
                .email()
                .with(new CustomEmailDomainValidator())
                .onValueChanged()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxValidationResult<EditText>>() {
                    @Override public void call(RxValidationResult<EditText> result) {
                        result.getItem().setError(result.isProper() ? null : result.getMessage());
                        Log.i(TAG, "Validation result " + result.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        Log.e(TAG, "Validation error", throwable);
                    }
                });

        RxValidator.createFor(holder.etGuestName)
                .nonEmpty()
                .minLength(5, "Min length is 5")
                .onFocusChanged()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxValidationResult<EditText>>() {
                    @Override public void call(RxValidationResult<EditText> result) {
                        result.getItem().setError(result.isProper() ? null : result.getMessage());
                        Log.i(TAG, "Validation result " + result.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        Log.e(TAG, "Validation error", throwable);
                    }
                });

        RxValidator.createFor(holder.etGuestMobile)
                .nonEmpty()
                .digitOnly()
                .minLength(10, "Mobil min length is 10")
                .onFocusChanged()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxValidationResult<EditText>>() {
                    @Override public void call(RxValidationResult<EditText> result) {
                        result.getItem().setError(result.isProper() ? null : result.getMessage());
                        Log.i(TAG, "Validation result " + result.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        Log.e(TAG, "Validation error", throwable);
                    }
                });


    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class GuestViewHolder extends RecyclerView.ViewHolder {

        EditText etGuestEmail, etGuestName, etGuestMobile;

        public GuestViewHolder(View itemView) {
            super(itemView);

            etGuestEmail = itemView.findViewById(R.id.etGuestEmail);
            etGuestName = itemView.findViewById(R.id.etGuestName);
            etGuestMobile = itemView.findViewById(R.id.etGuestMobile);

        }
    }
}
