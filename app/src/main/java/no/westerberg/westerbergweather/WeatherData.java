package no.westerberg.westerbergweather;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created  by Eline Westerberg on 08.04.2017.
 */
@Root(strict = false)
public class WeatherData {

    @Element
    private Forecast forecast;

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }
}

