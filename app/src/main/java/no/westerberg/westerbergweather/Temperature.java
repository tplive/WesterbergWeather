package no.westerberg.westerbergweather;

/**
 * Created  by Eline Westerberg on 08.04.2017.
 */

class Temperature {
    private double temperature;

    public double getTemperature() {
        return Math.round(temperature); //XML formatet krever at vi runder av verdien for å vise den.

    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
