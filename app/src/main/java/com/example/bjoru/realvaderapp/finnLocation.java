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

        //definerer myLat, myLong, geocoder og returdata
        myLat = params[0];
        myLong = params[1];
        Geocoder geocoder = new Geocoder(kontekst, Locale.getDefault());
        String returdata = "https://www.yr.no/sted/Norge/Nordland/Rana/Mo_i_Rana/varsel.xml";

        //finn adresse fra myLat og myLong med geocoder, lagt inne i en try and catch
        try {
            List<Address> myList = geocoder.getFromLocation(myLat, myLong, 1);
            Address address = myList.get(0);
            String myLoc = address.getLocality();

            //hent inn fil
            InputStream fil = kontekst.getResources().openRawResource(R.raw.noreg);
            String text = "";
            //sjekker om filen finnes og legger filen til String variablen text
            try {
                byte[] buffer = new byte[fil.available()];
                fil.read(buffer);
                fil.close();
                text = new String(buffer);
            } catch (IOException ex) {
                System.out.println("IO Exception"+" "+ex);
            }
            //splitter text variablen på \n og så \t
            String[] linjer = text.split("\n");
            for (String l: linjer) {
                String[] rader = l.split("\t");
                //sjekker om rader[1] som er stedsnavn i fila stemmer med det geocoder sier din location er
                if(rader[1].contains(myLoc)) {
                    //overskriver returdata variablen med rader[12] som er xml linken til stedsnavnet
                    returdata = rader[12];
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("IOException"+" "+ex);
        }
        //returnerer returdata som enten vil være XML til din location eller default som sender xml til Mo i Rana
        return returdata;
    }

    //AsyncResponse output som en String når prosessen er finished
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


