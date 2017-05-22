package com.example.bjoru.realvaderapp;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by BjoruLaptop on 03.05.2017.
 */

@Root(strict = false)
class WindDirection {
    @Attribute(required = false)
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
