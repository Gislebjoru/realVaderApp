package com.example.bjoru.realvaderapp;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by BjoruLaptop on 03.05.2017.
 */

@Root(strict = false)
class Pressure {

    @Attribute(required = false)
    String unit;

    @Attribute(required = false)
    double value;

    public String getUnit() {
        return unit;
    }

    public double getValue() {
        return value;
    }
}
