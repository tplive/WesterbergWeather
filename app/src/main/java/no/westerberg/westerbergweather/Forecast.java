package no.westerberg.westerbergweather;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.List;


/**
 * Created  by Eline Westerberg on 08.04.2017.
 */

@Root(strict=false)
class Forecast {

    @ElementList(name = "tabular", required = false)
    private List<Time> timeList;

    @Element
    Text text;

    public List<Time> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Time> timeList) {
        this.timeList = timeList;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}

