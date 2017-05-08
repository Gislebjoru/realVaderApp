package com.example.bjoru.realvaderapp;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by BjoruLaptop on 03.05.2017.
 */

@Root(strict = false)
class Symbol {

    @Attribute(required = false)
    int number;

    @Attribute(required = false)
    int numberEx;

    @Attribute(required = false)
    String name;

    @Attribute(required = false)
    String var;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberEx() {
        return numberEx;
    }

    public void setNumberEx(int numberEx) {
        this.numberEx = numberEx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
}
