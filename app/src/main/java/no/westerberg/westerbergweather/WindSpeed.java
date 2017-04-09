package no.westerberg.westerbergweather;

/**
 * Created by eline on 08.04.2017.
 */

public class WindSpeed {
    private double windspeed;

    public double getWindspeed() {
        return Math.round(windspeed);
    }

    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }
}

