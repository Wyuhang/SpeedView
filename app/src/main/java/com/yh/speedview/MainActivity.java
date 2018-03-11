package com.yh.speedview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.yh.speedview.view.Ammeter;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button mBtnRealTime;
//    Ammeter mAmmeter1;
    Ammeter mAmmeter2;
    Random mRandom;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        mBtnRealTime = findViewById (R.id.btn_real_time);
//        mAmmeter1 = findViewById (R.id.ammeter_1);
        mAmmeter2 = findViewById (R.id.ammeter_2);

        mRandom = new Random ();
        mBtnRealTime.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                int random = mRandom.nextInt (100);
                mAmmeter2.setRealTimeData (random);
            }
        });
    }
}
