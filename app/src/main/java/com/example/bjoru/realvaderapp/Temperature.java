package com.example.bjoru.realvaderapp;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by BjoruLaptop on 03.05.2017.
 */
@Root(strict = false)
class Temperature {
    @Attribute(required = false)
    String unit;

    @Attribute(required = false)
    int value;

    public String getUnit() {
        return unit;
    }

    public int getValue() {
        return value;
    }

    public void setUnit(String unit) {
        unit = unit;
    }

    public void setValue(int value) {
        value = value;
    }
}
