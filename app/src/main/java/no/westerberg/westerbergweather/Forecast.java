package no.westerberg.westerbergweather;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;


/**
 * Created by eline on 08.04.2017.
 */

@Root(strict=false)
public class Forecast {

    @ElementList(name = "Tabular")
    List<Time> timeList;

    public List<Time> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Time> timeList) {
        this.timeList = timeList;
    }
}

