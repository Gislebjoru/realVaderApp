package com.example.bjoru.realvaderapp;

import android.content.Context;
import android.location.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

/**
 * Created by bjoru on 21.05.2017.
 */

public class finnLocation extends AsyncTask<Double, Void, String> {
    final Context context = getApplicationContext();
    buildGoogleApiClient();
    protected static final String TAG = "MainActivity";
    protected GoogleApiClient mGoogleApiClient;
    protected android.location.Location mLastLocation;

    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;



    protected void doInBackground(double... params) {
        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));

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
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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


