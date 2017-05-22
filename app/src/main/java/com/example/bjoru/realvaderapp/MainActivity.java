package com.example.bjoru.realvaderapp;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    final int LOCATION_REQUEST_CODE = 1;
    protected static final String TAG = "MainActivity";
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildGoogleApiClient();

        final ImageView mySymbol = (ImageView) findViewById(R.id.imageView2);

        final Intent intent = new Intent(this, Langtidsvarsel.class);
        final Intent intent2 = new Intent(this, sokActivity.class);

        final TextView tempText = (TextView)findViewById(R.id.tempText);
        final TextView wsText = (TextView)findViewById(R.id.wsText);
        final TextView wdText = (TextView)findViewById(R.id.wdText);
        final TextView pressureText = (TextView)findViewById(R.id.presText);
        final TextView sted = (TextView) findViewById(R.id.sted);

        Intent mottaXML = getIntent();
        String url="";

        if(mottaXML.hasExtra("url")) {
            url = mottaXML.getExtras().getString("url");
        }
        else {
            url = "https://www.yr.no/sted/Norge/Nordland/Rana/Mo_i_Rana/varsel.xml";
        }

        intent.putExtra("url", url);

        new VaderData(new VaderData.AsyncResponse() {

            @Override
            public void processFinish(WeatherData output) {

                String ws = "ss"+output.getForecast().getTimeList().get(0).getSymbol().getVar().replace("mf/", "").replace(".", "_");
                mySymbol.setImageResource(getResources().getIdentifier(ws, "drawable", getPackageName()));

                List<Time> timeList = output.getForecast().getTimeList();
                final String timeFrom = output.getForecast().getTimeList().get(0).getFrom();
                final String timeTo = output.getForecast().getTimeList().get(0).getTo();
                final Temperature temp = output.getForecast().getTimeList().get(0).getTemperature();
                WindSpeed wspeed = output.getForecast().getTimeList().get(0).getWindSpeed();
                Pressure pressure = output.getForecast().getTimeList().get(0).getPressure();
                WindDirection wdir = output.getForecast().getTimeList().get(0).getWindDirection();
                String myStad = output.getLocation().getName();

                tempText.setText(String.valueOf(temp.getValue()+" "+temp.getUnit()));
                wsText.setText(String.valueOf(wspeed.getMps()+" Meter i sekundet, "+wspeed.getName()));
                wdText.setText(String.valueOf(wdir.getName()+" Vindretning"));
                sted.setText(String.valueOf(myStad));

                if (pressure.getValue() > 1013) {
                    pressureText.setText(String.valueOf("Høytrykk"+"("+pressure.getValue()+" "+pressure.getUnit()+")"));
                } else {
                    pressureText.setText(String.valueOf("Lavtrykk"+"("+pressure.getValue()+" "+pressure.getUnit()+")"));
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

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            double myLat = mLastLocation.getLatitude();
            double myLong = mLastLocation.getLongitude();
            final Intent forrigeIntent = getIntent();
            final Intent main = new Intent(this, MainActivity.class);
            new finnLocation(this, new finnLocation.AsyncResponse() {
                String url ="";
                @Override
                public void processFinished(String output) {
                    url = output;
                    if (!forrigeIntent.hasExtra("url")) {
                        main.putExtra("url", url);
                        startActivity(main);
                    }
                }
            }).execute(myLat,myLong);
        } else {
            Toast.makeText(this, "No location detected", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case LOCATION_REQUEST_CODE:
                if(grantResults.length > 0) {
                    System.out.println("Access to my location granted");
                } else {
                    System.out.println("Access to my location denied");
                }
        }
    }
}
