package no.westerberg.westerbergweather;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.net.URL;

/**
 * Created by Thomas Qvidahl on 03.05.2017.
 * For obligatorisk visning av link i appen.
 */
class Link {

    @Attribute(name="text")
    private String text;
    @Attribute(name="url")
    private String url;

    public void Credit(String text, String url) {
        this.text = text;
        this.url = url;
    }
}

public class Credit {

    @Element(name="link")
    private Link link;

    public Credit(Link link) {
        this.link = link;
    }
}
