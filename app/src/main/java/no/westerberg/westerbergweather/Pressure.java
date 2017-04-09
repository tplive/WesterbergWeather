package no.westerberg.westerbergweather;

/**
 * Created by eline on 08.04.2017.
 */

public class Pressure {
    private double pressure;

    public double getPressure() {
        return Math.round(pressure); //XML formatet krever at vi runder av verdien for å vise den.
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
}

