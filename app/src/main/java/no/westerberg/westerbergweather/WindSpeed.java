package no.westerberg.westerbergweather;

/**
 * Created  by Eline Westerberg on 08.04.2017.
 */

class WindSpeed {
    private double windspeed;

    public double getWindspeed() {
        return Math.round(windspeed); //XML formatet krever at vi runder av verdien for å vise den.
    }

    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }
}

