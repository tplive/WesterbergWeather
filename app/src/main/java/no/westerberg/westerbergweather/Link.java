package no.westerberg.westerbergweather;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by thomasqvidahl on 26.05.2017.
 */
@Root(strict=false)
class Link {

    @Attribute
    private String text;

    @Attribute
    private String url;

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}