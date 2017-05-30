package com.example.bjoru.realvaderapp;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Winlab3 on 30.05.2017.
 */
@Root(strict = false)
public class Credit {
    @Element(required = false)
    Link link;

    public Link getLink() {
        return link;
    }
}
