package no.westerberg.westerbergweather;

/**
 * Created by eline on 08.04.2017.
 */

public class Temperature {
    private double temperature;

    public double getTemperature() {
        return Math.round(temperature);
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
