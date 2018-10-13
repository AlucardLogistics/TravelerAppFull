package com.example.sadic.travelerapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.sadic.travelerapp.R;
import com.example.sadic.travelerapp.ui.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.imageView2)
    ImageView imageView2;
    Animation animSlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);



        final Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
                    imageView2.startAnimation(animSlide);
                    Thread.sleep(6000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                }

                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);



            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out);
    }
}
