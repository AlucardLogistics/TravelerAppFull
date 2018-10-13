package com.example.sadic.travelerapp.ui.payment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sadic.travelerapp.R;
import com.github.phajduk.rxvalidator.RxValidationResult;
import com.github.phajduk.rxvalidator.RxValidator;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = "PaymentActivity";

    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPaswword)
    EditText etPaswword;
    @BindView(R.id.etMobile)
    EditText etMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ButterKnife.bind(this);

        RxValidator.createFor(etEmail)
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

        RxValidator.createFor(etPaswword)
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

        RxValidator.createFor(etMobile)
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
}
