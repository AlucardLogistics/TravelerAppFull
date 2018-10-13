package com.example.sadic.travelerapp.ui.booking;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sadic.travelerapp.R;
import com.example.sadic.travelerapp.adapters.GuestAdapter;
import com.example.sadic.travelerapp.data.model.Guest;
import com.example.sadic.travelerapp.data.network.model.CouponsItem;
import com.example.sadic.travelerapp.ui.payment.PaymentActivity;
import com.example.sadic.travelerapp.ui.payment.RecepitActivity;
import com.example.sadic.travelerapp.utils.SharedPref;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity implements IViewBooking {
    private static final String TAG = "BookingActivity";

    private static final int PAYPAL_REQUEST_CODE = 101;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(SharedPref.PAYPAL_CLIENT_ID)
            .merchantName("Alucard Logistics");

    IPresenterBooking presenterBooking;

    //seats info
    TextView tvSeatsBooked;

    //trip info
    TextView tvRouteName, tvBustTYpe, tvFare,
            tvDepartTime, tvArrivalTime,tvDurationTime;

    //contact info
    TextView tvBookingEmailId, tvBookingName, tvBookingMobile;

    //coupon view
    EditText etCoupon;
    Button btApplyCoupon;
    TextView tvCouponQuestion, tvCouponInvalid;
    TextWatcher twCoupon;

    //confirmBook
    Button btConfirmBook;

    //guests
    RecyclerView rvGuests;
    GuestAdapter adapter;
    List<Guest> guestList = new ArrayList<>();

    //coupon Lists
    List<String> couponNo = new ArrayList<>();
    String amount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        SharedPref.init(this);

        //start paypal service
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        tvSeatsBooked = findViewById(R.id.tvBookingSeats);

        init();

        presenterBooking = new PresenterBooking(this);
        presenterBooking.getActivityDate();


    }

    void init() {

        //trip info setup
        tvRouteName = findViewById(R.id.tvBusBookRoute);
        tvRouteName.setText(SharedPref.read(SharedPref.ROUTE_NAME, ""));
        tvBustTYpe = findViewById(R.id.tvBusBookType);
        tvBustTYpe.setText(SharedPref.read(SharedPref.BUS_TYPE, ""));
        tvFare = findViewById(R.id.tvBusBookFare);
        tvDepartTime = findViewById(R.id.tvBusBookDeparture);
        tvDepartTime.setText(SharedPref.read(SharedPref.ROUTE_DEPARTURE, ""));
        tvArrivalTime = findViewById(R.id.tvBusArrival);
        tvArrivalTime.setText(SharedPref.read(SharedPref.ROUTE_ARRIVAL, ""));
        tvDurationTime = findViewById(R.id.tvBusTravelTime);
        tvDurationTime.setText(SharedPref.read(SharedPref.ROUTE_DURATION, ""));

        //user info
        tvBookingEmailId = findViewById(R.id.tvBookingEmailId);
        tvBookingEmailId.setText(SharedPref.read(SharedPref.EMAIL, ""));
        tvBookingName = findViewById(R.id.tvBookingName);
        tvBookingName.setText(SharedPref.read(SharedPref.DISPLAY_NAME, ""));
        tvBookingMobile = findViewById(R.id.tvBookingMobile);
        if(tvBookingMobile.getText().toString().isEmpty()){
            tvBookingMobile.setText(SharedPref.read(SharedPref.MOBILE, ""));
        } else {
            tvBookingMobile.setText("5616030012");

        }

        //coupon view
        etCoupon = findViewById(R.id.etCoupon);
        etCoupon.setVisibility(View.GONE);
        tvCouponQuestion = findViewById(R.id.tvCouponQuestion);
        btApplyCoupon = findViewById(R.id.btApplyCoupon);
        btApplyCoupon.setVisibility(View.GONE);
        tvCouponInvalid = findViewById(R.id.tvCouponInvalid);
        tvCouponInvalid.setVisibility(View.GONE);

        String seatsBooked = getIntent().getStringExtra("seatsBooked");
        String seatLabels = getIntent().getStringExtra("seatsNumbers");
        //Log.d(TAG, "init: seatsReserved: " + seatLabels.toString());
        int seats = Integer.parseInt(seatsBooked);
        int fare  = Integer.parseInt(SharedPref.read(SharedPref.ROUTE_FARE, ""));
        int totalFare = fare * seats;
        tvFare.setText(String.valueOf(totalFare));
        tvSeatsBooked.setText("Seats: " + seatsBooked);

        //confirm booking view
        btConfirmBook = findViewById(R.id.btConfirmBook);

        //guests
        rvGuests = findViewById(R.id.rvGuests);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvGuests.setLayoutManager(manager);
        rvGuests.setItemAnimator(new DefaultItemAnimator());
        adapter = new GuestAdapter(this, guestList, seats - 1);
        rvGuests.setAdapter(adapter);


        //setup coupon
        tvCouponQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCoupon.getVisibility() == View.GONE) {
                    etCoupon.setVisibility(View.VISIBLE);
                    etCoupon.addTextChangedListener(twCoupon);
                } else {
                    etCoupon.setVisibility(View.GONE);
                    etCoupon.setText("");
                    btApplyCoupon.setVisibility(View.GONE);
                    tvCouponInvalid.setVisibility(View.GONE);
                }
            }
        });

        btApplyCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coupon =  etCoupon.getText().toString();
                boolean isMatch = false;
                for(int i =0; i<couponNo.size(); i++) {
                    Log.d(TAG, "onClick: codes: " + couponNo.get(i));
                    if(coupon != null && coupon.equals(couponNo.get(i))) {
                        Log.d(TAG, "onClick: et: " + coupon + " couponNO: " + couponNo.get(i));
                        isMatch = true;
                        break;
                    }
                }
                if(isMatch) {
                    Toast.makeText(BookingActivity.this, "Coupon has been applied.", Toast.LENGTH_SHORT).show();
                    int fare = Integer.parseInt(tvFare.getText().toString());
                    int discountFare = fare - fare/10;
                    tvFare.setText(String.valueOf(discountFare));
                    tvCouponInvalid.setVisibility(View.GONE);
                    etCoupon.setText("");
                } else {
                    tvCouponInvalid.setVisibility(View.VISIBLE);
                }
            }
        });

        twCoupon = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String couponInput = etCoupon.getText().toString().trim();

                if(!couponInput.isEmpty()) {
                    btApplyCoupon.setVisibility(View.VISIBLE);
                } else {
                    btApplyCoupon.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        btConfirmBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: guestList: " + guestList.toString());
                Log.d(TAG, "onClick: guestList: " );
                processPayment();
            }
        });
    }

    private void processPayment() {
        amount = tvFare.getText().toString();
        PayPalPayment ppp =
                new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD",
                        "Thank you for your purchase.", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent i = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        i.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, ppp);
        startActivityForResult(i, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PAYPAL_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, RecepitActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", amount)
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if(resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        }  else if(resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, PayPalService.class));
    }

    @Override
    public void showCoupons(List<CouponsItem> couponsItemList) {
        Log.d(TAG, "showCoupons: " + couponsItemList.toString());
        for(int i = 0; i < couponsItemList.size(); i++) {

                   couponNo.add(couponsItemList.get(i).getCouponno());
        }
        Log.d(TAG, "showCoupons: couponNO: " + couponNo.toString());

    }
}
