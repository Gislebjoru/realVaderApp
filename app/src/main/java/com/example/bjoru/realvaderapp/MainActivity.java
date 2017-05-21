package com.example.bjoru.realvaderapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.*;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

public class MainActivity extends AppCompatActivity  {

    final int LOCATION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, Langtidsvarsel.class);
        final Intent intent2 = new Intent(this, sokActivity.class);

        final TextView tempText = (TextView)findViewById(R.id.tempText);
        final TextView wsText = (TextView)findViewById(R.id.wsText);
        final TextView wdText = (TextView)findViewById(R.id.wdText);
        final TextView pressureText = (TextView)findViewById(R.id.presText);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        } else {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

            LocationManager myLocM = (LocationManager) getSystemService(LOCATION_SERVICE);
            android.location.Location myLoc = myLocM.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double myLat;
            double myLong;
            if (myLoc != null) {
                myLat = myLoc.getLatitude();
                myLong = myLoc.getLongitude();
            } else {
                myLat = 0.0;
                myLong = 0.0;
            }

            new finnLocation(this, new finnLocation.AsyncResponse() {
                String url ="";
                @Override
                public void processFinished(String output) {
                    url = output;

                }
            }).execute(myLat, myLong);

        }

        Intent mottaXML = getIntent();
        String url="";
        if(mottaXML.hasExtra("url")) {
            url = mottaXML.getExtras().getString("url");
        }
        else {
            url = "https://www.yr.no/sted/Norge/Nordland/Rana/Mo_i_Rana/varsel.xml";
            System.out.println(url);
        }

        new VaderData(new VaderData.AsyncResponse() {
            @Override
            public void processFinish(WeatherData output) {
                List<Time> timeList = output.getForecast().getTimeList();
                final String timeFrom = output.getForecast().getTimeList().get(0).getFrom();
                final String timeTo = output.getForecast().getTimeList().get(0).getTo();
                final Temperature temp = output.getForecast().getTimeList().get(0).getTemperature();
                WindSpeed wspeed = output.getForecast().getTimeList().get(0).getWindSpeed();
                Pressure pressure = output.getForecast().getTimeList().get(0).getPressure();
                WindDirection wdir = output.getForecast().getTimeList().get(0).getWindDirection();

                tempText.setText(String.valueOf(temp.getValue()+" "+temp.getUnit()));
                wsText.setText(String.valueOf(wspeed.getMps()+" Meter i sekundet, "+wspeed.getName()));
                wdText.setText(String.valueOf(wdir.getName()+" Vindretning"));
                if (pressure.getValue() > 1013) {
                    pressureText.setText(String.valueOf("Høytrykk"+"("+pressure.getValue()+" hPa)"));
                } else {
                    pressureText.setText(String.valueOf("Lavtrykk"+"("+pressure.getValue()+" hPa)"));
                }

                final Button langtidsvarsel = (Button) findViewById(R.id.button2);
                langtidsvarsel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        startActivity(intent);
                    }
                });
            }
        }).execute(url);



        final Button sok = (Button) findViewById(R.id.sokeKnapp);
        sok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(intent2);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case LOCATION_REQUEST_CODE:
                if(grantResults.length > 0) {
                    //FIXPLS
                } else {
                    //FIXPLS
                }
        }
    }
}
