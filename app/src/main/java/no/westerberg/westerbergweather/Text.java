package no.westerberg.westerbergweather;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by thomasqvidahl on 26.05.2017.
 */
@Root(strict=false)
public class Text {

    @ElementList(name = "location", required = false)
    List<Time> timeList;

    public List<Time> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Time> timeList) {
        this.timeList = timeList;
    }
}
