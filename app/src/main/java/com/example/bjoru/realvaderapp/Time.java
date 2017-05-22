package com.example.bjoru.realvaderapp;



import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Time {
    @Attribute(required = false)
    String from;

    @Attribute(required = false)
    String to;

    @Element(required = false)
    Symbol symbol;

    @Element(required = false)
    WindDirection windDirection;

    @Element(required = false)
    WindSpeed windSpeed;

    @Element(required = false)
    Temperature temperature;

    @Element(required = false)
    Pressure pressure;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public WindDirection getWindDirection() {
        return windDirection;
    }

    public WindSpeed getWindSpeed() {
        return windSpeed;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Pressure getPressure() {
        return pressure;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}