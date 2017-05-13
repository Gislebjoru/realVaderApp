package com.example.bjoru.realvaderapp;

import android.content.Intent;
import android.os.AsyncTask;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;



/**
 * Created by BjoruLaptop on 04.05.2017.
 */

public class VaderData extends AsyncTask<String, Void, WeatherData>{

    final public static String url = "https://www.yr.no/sted/Norge/Nordland/Rana/Mo/varsel.xml";
    //final public static String[] newUrl = url.split("/");
    //final public static String finalurl = "https://www.yr.no/sted/Norge/Nord-trøndelag/Namsos/"+city+"/varsel.xml";

    @Override
    protected WeatherData doInBackground(String... params) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

        ResponseEntity<WeatherData> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, WeatherData.class);

        WeatherData weatherData = responseEntity.getBody();

        return weatherData;

    }

    public interface AsyncResponse {
        void processFinish(WeatherData output);
    }

    public AsyncResponse delegate = null;

    public VaderData(AsyncResponse delegate) {
        this.delegate = delegate;
    }


    protected void onPostExecute(WeatherData result) {
        delegate.processFinish(result);

    }
}