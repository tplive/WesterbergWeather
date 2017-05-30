package no.westerberg.westerbergweather;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thomasqvidahl on 30.05.2017.
 */
@Root(strict = false)
class Meta {

    @Element
    String lastupdate;

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }
}
