package com.example.bjoru.realvaderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
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
        final Intent goBack = new Intent(this, MainActivity.class);

        final Button tilbakeKnapp = (Button) findViewById(R.id.tilbakeKnapp2);
        tilbakeKnapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(goBack);
            }
        });

        Intent url = getIntent();
        String myUrl = url.getExtras().getString("url");

        new VaderData(new VaderData.AsyncResponse() {
            @Override
            public void processFinish(WeatherData output) {

                List<Time> timeList = output.getForecast().getTimeList();
                String longtime = "";

                for(int i=1;i<timeList.size();i++) {
                    Time t = timeList.get(i);
                    String splitFrom = String.valueOf(t.getFrom());
                    String splitTo = String.valueOf(t.getTo());
                    String[] toSplit = splitTo.split("T");
                    String[] toSplit2 = toSplit[0].split("-");
                    String[] fromSplit = splitFrom.split("T");
                    String[] fromSplit2 = fromSplit[0].split("-");
                    longtime += String.valueOf(fromSplit2[2]+" "+fromSplit2[1]+" "+fromSplit2[0]+" "+fromSplit[1]+" - "
                            +toSplit2[2]+" "+toSplit2[1]+" "+toSplit2[0]+" "+toSplit[1]+" "+t.getTemperature().getValue()+" "
                            +t.getTemperature().getUnit()+"\n"+"\n");
                }
                langtid.setText(longtime);
                langtid.setMovementMethod(new ScrollingMovementMethod());
            }
        }).execute(myUrl);
    }

}
