package com.example.bjoru.realvaderapp;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by BjoruLaptop on 03.05.2017.
 */
@Root(strict = false)
class WindSpeed {

    @Attribute(required = false)
    double mps;

    @Attribute(required = false)
    String name;

    public double getMps() {
        return mps;
    }

    public void setMps(int mps) {
        this.mps = mps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}