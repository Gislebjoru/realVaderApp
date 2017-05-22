package com.example.bjoru.realvaderapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

/**
 * Created by bjoru on 21.05.2017.
 */

public class finnLocation extends AsyncTask<Double, Void, String> {

    private Context kontekst;
    double myLat;
    double myLong;

    protected String doInBackground(Double... params) {

        myLat = params[0];
        myLong = params[1];
        Geocoder geocoder = new Geocoder(kontekst, Locale.getDefault());
        String returdata = "http://www.yr.no/sted/Norge/Finnmark/Karasjok/Karasjok/varsel.xml";

        try {
            List<Address> myList = geocoder.getFromLocation(myLat, myLong, 1);
            Address address = myList.get(0);
            String myLoc = address.getLocality();
            InputStream fil = kontekst.getResources().openRawResource(R.raw.noreg);
            String text = "";
            try {
                byte[] buffer = new byte[fil.available()];
                fil.read(buffer);
                fil.close();
                text = new String(buffer);
            } catch (IOException ex) {
                System.out.println("IO Exception"+" "+ex);
            }
            String[] linjer = text.split("\n");
            for (String l: linjer) {
                String[] rader = l.split("\t");
                if(rader[1].contains(myLoc)) {
                    returdata = rader[12];
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("IOException"+" "+ex);
        }
        return returdata;
    }

    public interface AsyncResponse {
        void processFinished(String output);
    }

    public AsyncResponse delegate = null;

    public finnLocation(Context kontekst, AsyncResponse delegate) {
        this.delegate = delegate;
        this.kontekst = kontekst;
    }

    protected void onPostExecute(String result) {
        delegate.processFinished(result);
    }
}


