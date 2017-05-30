package com.example.bjoru.realvaderapp;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class WeatherData {
    @Element(required = false)
    Forecast forecast;

    @Element(required = false)
    Location location;

    @Element(required = false)
    Credit credit;

    public Credit getCredit() {
        return credit;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public Location getLocation() {
        return location;
    }
}
