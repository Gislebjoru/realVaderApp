package com.example.bjoru.realvaderapp;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by Winlab3 on 30.05.2017.
 */
@Root(strict = false)
public class Link {
    @Attribute(required = false)
    String text;

    @Attribute(required = false)
    String url;

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }
}
