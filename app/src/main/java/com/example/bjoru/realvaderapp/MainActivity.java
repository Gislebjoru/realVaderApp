package com.example.bjoru.realvaderapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    final int LOCATION_REQUEST_CODE = 1;
    protected static final String TAG = "MainActivity";
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;

    private double myLat;
    private double myLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));

        buildGoogleApiClient();

        //LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        //android.location.Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        /*double myLat;
        double myLong;
        if (location != null) {
            myLat = location.getLatitude();
            myLong = location.getLongitude();
        }
        else {
            myLat = 66.312776951594;
            myLong = 14.1427847841911;
        }
*/

        final Context context = getApplicationContext();

        final Intent intent = new Intent(this, Langtidsvarsel.class);
        final Intent intent2 = new Intent(this, sokActivity.class);

        final TextView tempText = (TextView)findViewById(R.id.tempText);
        final TextView wsText = (TextView)findViewById(R.id.wsText);
        final TextView wdText = (TextView)findViewById(R.id.wdText);
        final TextView pressureText = (TextView)findViewById(R.id.presText);

        Intent mottaXML = getIntent();
        String url="";
        if(mottaXML.hasExtra("url")) {
            url = mottaXML.getExtras().getString("url");
        }
        else {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> myList = geocoder.getFromLocation(myLat, myLong, 1);
                Address address = myList.get(0);
                String myLoc = address.getLocality();
                InputStream fil = context.getResources().openRawResource(R.raw.noreg);
                String text = "";
                try {
                    byte[] buffer = new byte[fil.available()];
                    fil.read(buffer);
                    fil.close();
                    text = new String(buffer);
                } catch (IOException ex) {
                    System.out.println("IO Exception");
                }
                String[] linjer = text.split("\n");
                url = "https://www.yr.no/sted/Norge/Nordland/Rana/Mo_i_Rana/varsel.xml";
                for (String l: linjer) {
                    String[] rader = l.split("\t");
                    if(rader[1].contains(myLoc)) {
                        url = rader[12];
                        break;
                    }
                }
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Index out of bounds Exception");
            } catch (IOException ex) {
                System.out.println("IO Exception");
            }
        }


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        } else {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
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

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case LOCATION_REQUEST_CODE:
                if(grantResults.length > 0) {

                } else {

                }
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
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel,
                    mLastLocation.getLatitude()));
            mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel,
                    mLastLocation.getLongitude()));

            myLat = mLastLocation.getLatitude();
            myLong = mLastLocation.getLongitude();
        } else {
            Toast.makeText(this, "No location detected", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }
}
