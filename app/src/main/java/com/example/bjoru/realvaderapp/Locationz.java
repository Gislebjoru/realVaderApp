package com.example.bjoru.realvaderapp;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Locationz {
    @Element(required = false)
    String name;

    @Element(required = false)
    String type;

    @Element(required = false)
    String country;

    //GETSET Name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //GETSET Type
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    //GETSET Country
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}
