package com.example.shimaashokry.mobileapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GPSOutput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsoutput);
        TextView LongView = (TextView) findViewById(R.id.Long);
        TextView LatView = (TextView) findViewById(R.id.Lat);


        Bundle b = getIntent().getExtras();
        String Longitude = b.getString("Longitude");
        String Latitude = b.getString("Latitude");

        LongView.setText(Longitude);
        LatView.setText(Latitude);


    }
}
