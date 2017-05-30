package no.westerberg.westerbergweather;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created  by Eline Westerberg on 08.04.2017.
 * Rot-element i XML data fra Yr
 */
@Root(strict = false)
class WeatherData {

    @Element
    private Forecast forecast;
    @Element
    private Location location;
    @Element
    private Credit credit;

    public Forecast getForecast() {

        return forecast;
    }

    public void setForecast(Forecast forecast) {

        this.forecast = forecast;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {

        this.location = location;
    }

    public Credit getCredit() {
        return credit;
    }
    public void setCredit(Credit credit) {

        this.credit = credit;
    }

}

