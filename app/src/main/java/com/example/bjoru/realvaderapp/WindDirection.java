package com.example.bjoru.realvaderapp;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by BjoruLaptop on 03.05.2017.
 */

@Root(strict = false)
class WindDirection {

    @Attribute(required = false)
    double deg;

    @Attribute(required = false)
    String code;

    @Attribute(required = false)
    String name;

    public double getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
