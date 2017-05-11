package com.example.bjoru.realvaderapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Langtidsvarsel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_langtidsvarsel);

        final TextView langtid = (TextView) findViewById(R.id.langtidsvarsel);
        //String message = getIntent().getStringExtra(MainActivity.myString);


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

                for(int i=1;i<timeList.size();i++) {
                    Time t = timeList.get(i);
                    langtid.setText(String.valueOf(timeFrom+" "+timeTo+", "+temp.getValue()+" "+temp.getUnit()+", "+wspeed.getName()+", "+wdir.getName()));
                    System.out.println(i);

                }

            }
        }).execute();



    }

}
