package no.westerberg.westerbergweather;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.net.URL;

/**
 * Created by Thomas Qvidahl on 03.05.2017.
 * For obligatorisk visning av link i appen.
 */
@Root(strict=false)
public class Credit {

    @Element
    private Link link;

    public void setLink(Link link) {
        this.link = link;
    }

    public Link getLink() {
        return link;
    }
}
