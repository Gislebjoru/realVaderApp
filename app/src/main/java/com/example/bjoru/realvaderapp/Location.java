package com.example.bjoru.realvaderapp;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Location {
    @Element(required = false)
    String name;

    //GETSET Name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
