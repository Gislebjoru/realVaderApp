package com.example.bjoru.realvaderapp;

import android.os.AsyncTask;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;



/**
 * Created by BjoruLaptop on 04.05.2017.
 */

public class VaderData extends AsyncTask<String, Void, WeatherData>{


    @Override
    protected WeatherData doInBackground(String... params) {

        String url = params[0];
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