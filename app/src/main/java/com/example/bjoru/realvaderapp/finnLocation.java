package com.example.bjoru.realvaderapp;

import android.app.ProgressDialog;
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

    private Context context;
    private ProgressDialog progressDialog;

    double myLat;
    double myLong;

    protected String doInBackground(Double... params) {

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            //dosomethinghereprobably
            Thread.currentThread().interrupt();
        }

        myLat = params[0];
        myLong = params[1];
        System.out.println("there should be latitude here...."+params[0]);



        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String returdata = "https://www.yr.no/sted/Norge/Nordland/Rana/Mo_i_Rana/varsel.xml";
        try {
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
            for (String l: linjer) {
                String[] rader = l.split("\t");
                if(rader[1].contains(myLoc)) {
                    returdata = rader[12];
                    break;
                }
            }
        } catch (IOException ex) {
            //FIXMEPLS
        }
        return returdata;

    }

    public interface AsyncResponse {
        void processFinished(String output);
    }

    public AsyncResponse delegate = null;

    public finnLocation(Context context, AsyncResponse delegate) {
        this.delegate = delegate;
        this.context = context;
    }

    protected void onPostExecute(String result) {
        delegate.processFinished(result);
    }
}


