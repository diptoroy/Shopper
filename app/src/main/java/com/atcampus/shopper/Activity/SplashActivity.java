package com.atcampus.shopper.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.atcampus.shopper.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SystemClock.sleep(3500);
        Intent loginIntent = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
