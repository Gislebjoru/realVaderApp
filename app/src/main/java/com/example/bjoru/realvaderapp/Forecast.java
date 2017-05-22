package com.example.bjoru.realvaderapp;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict=false)
public class Forecast {
    @ElementList(name="tabular")
    List<Time> timeList;

    public List<Time> getTimeList() {
        return timeList;
    }
}
