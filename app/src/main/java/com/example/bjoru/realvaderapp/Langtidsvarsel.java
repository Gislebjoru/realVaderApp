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

        //finner Textview og navngir den
        final TextView langtid = (TextView) findViewById(R.id.langtidsvarsel);
        //lager en intent for å gå tilbake til MainActivity
        final Intent goBack = new Intent(this, MainActivity.class);

        /*finner Button og navngir den, setter onclicklistener og sier
        at den ska starte goBack intenten som sender deg tilbake til MainActivity*/
        final Button tilbakeKnapp = (Button) findViewById(R.id.tilbakeKnapp2);
        tilbakeKnapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(goBack);
            }
        });

        //henter intent extra
        Intent url = getIntent();
        String myUrl = url.getExtras().getString("url");

        //ny VaderData AsyncResponse
        new VaderData(new VaderData.AsyncResponse() {
            @Override
            public void processFinish(WeatherData output) {

                //definerer timeList og longtime
                List<Time> timeList = output.getForecast().getTimeList();
                String longtime = "";

                //forloop som henter ut alle elementer i timeList og splitter dem
                for(int i=1;i<timeList.size();i++) {
                    Time t = timeList.get(i);
                    String splitFrom = String.valueOf(t.getFrom());
                    String splitTo = String.valueOf(t.getTo());
                    String[] toSplit = splitTo.split("T");
                    String[] toSplit2 = toSplit[0].split("-");
                    String[] fromSplit = splitFrom.split("T");
                    String[] fromSplit2 = fromSplit[0].split("-");
                    //legger splittene inn i longtime i det formated jeg ønsker.
                    longtime +=
                            String.valueOf(fromSplit2[2]+"/"+fromSplit2[1]+" Kl: "+fromSplit[1]+" - "
                            +toSplit[1]+", Temperatur: "+t.getTemperature().getValue()+" "
                            +t.getTemperature().getUnit()+"\n"+"\n");
                }
                //setter teksten i longtime til langtid TextView og implementerer scrolling
                langtid.setText(longtime);
                langtid.setMovementMethod(new ScrollingMovementMethod());
            }
            //utfører AsyncResponsen
        }).execute(myUrl);
    }

}
