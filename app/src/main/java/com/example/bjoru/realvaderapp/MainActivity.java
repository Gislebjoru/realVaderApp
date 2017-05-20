package com.example.bjoru.realvaderapp;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    //public static final String myString = "myString";
    //public static String city = "";

    protected static final String TAG = "MainActivity";

    protected GoogleApiClient mGoogleApiClient;

    protected Location mLastlocation;

    protected String mLatitudeLabel;
    protected String mLongitudeLabel;

    protected TextView mLatitudeText;
    protected TextView mLongitudeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mLatitudeLabel = getResources().getString(R.string.latitude_label);
        //mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLatitudeText = (TextView) findViewById(R.id.latitude_text);
        mLongitudeText = (TextView) findViewById(R.id.longitude_text);

        buildGoogleApiClient();

        final Intent intent = new Intent(this, Langtidsvarsel.class);



        final TextView tempText = (TextView)findViewById(R.id.tempText);
        final TextView wsText = (TextView)findViewById(R.id.wsText);
        final TextView wdText = (TextView)findViewById(R.id.wdText);
        final TextView pressureText = (TextView)findViewById(R.id.presText);
        //final ImageView symbolImg = (ImageView)findViewById(R.id.myImg);
       // final TextView testFelt = (TextView) findViewById(R.id.textView2);
        final EditText sokeFelt = (EditText) findViewById(R.id.sokeFelt);

        new VaderData(new VaderData.AsyncResponse() {
            @Override
            public void processFinish(WeatherData output) {

                //String test = String.valueOf(VaderData.newUrl[5]+" "+VaderData.newUrl[6]+" "+VaderData.newUrl[7]);
                List<Time> timeList = output.getForecast().getTimeList();
                final String timeFrom = output.getForecast().getTimeList().get(0).getFrom();
                final String timeTo = output.getForecast().getTimeList().get(0).getTo();
                final Temperature temp = output.getForecast().getTimeList().get(0).getTemperature();
                WindSpeed wspeed = output.getForecast().getTimeList().get(0).getWindSpeed();
                Pressure pressure = output.getForecast().getTimeList().get(0).getPressure();
                WindDirection wdir = output.getForecast().getTimeList().get(0).getWindDirection();
                Symbol symbol = output.getForecast().getTimeList().get(0).getSymbol();

                System.out.println(String.valueOf("Temperatur: "+temp.getValue())+" "+temp.getUnit());
                System.out.println(String.valueOf("Vindstyrke: "+wspeed.getMps())+" Meter i sekundet, "+wspeed.getName());
                System.out.println(String.valueOf("Lufttrykk: "+pressure.getValue()+" "+pressure.getUnit()));
                System.out.println(String.valueOf("Vindretning: "+wdir.getDeg()+" "+wdir.getCode()+" "+wdir.getName()));
                System.out.println(symbol);
                tempText.setText(String.valueOf(temp.getValue()+" "+temp.getUnit()));
                wsText.setText(String.valueOf(wspeed.getMps()+" Meter i sekundet, "+wspeed.getName()));
                wdText.setText(String.valueOf(wdir.getName()+" Vindretning"));
                if (pressure.getValue() > 1013) {
                    pressureText.setText(String.valueOf("Høytrykk"+"("+pressure.getValue()+" hPa)"));
                } else {
                    pressureText.setText(String.valueOf("Lavtrykk"+"("+pressure.getValue()+" hPa)"));
                }
                //System.out.println(test);


                //symbolImg.setImageResource(R.drawable.class);

                final Button langtidsvarsel = (Button) findViewById(R.id.button2);
                langtidsvarsel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        //String message = String.valueOf(timeFrom+"-"+timeTo);
                        //intent.putExtra(myString, t);
                        startActivity(intent);
                    }
                });



            }
        }).execute();



        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //city.setText(String.valueOf(sokeFelt.getText()));
                //city = String.valueOf(sokeFelt.getText());
                //System.out.println(city);
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
    public void onConnected(Bundle connectionHint) {

        mLastlocation= LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastlocation != null) {
            mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel,
                    mLastlocation.getLatitude()));
            mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel,
                    mLastlocation.getLongitude()));
        } else {
            Toast.makeText(this, "No location detected", Toast.LENGTH_LONG).show();
        }

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: connectionResult.getErrorCode() = " + result.getErrorCode());
    }
}
