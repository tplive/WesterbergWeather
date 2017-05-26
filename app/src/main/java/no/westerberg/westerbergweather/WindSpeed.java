package no.westerberg.westerbergweather;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created  by Eline Westerberg on 08.04.2017.
 */

@Root(strict = false)
class WindSpeed {

    @Attribute
    private double mps;
    @Attribute
    private String name;

    public double getMps() {
        return Math.round(mps); //XML formatet krever at vi runder av verdien for å vise den.
    }

    public String getName() {
        return name;
    }

    public void setMps(double mps) {
        this.mps = mps;
    }

    public void setName(String name) {
        this.name = name;
    }
}

