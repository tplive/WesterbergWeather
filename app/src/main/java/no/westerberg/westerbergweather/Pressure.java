package no.westerberg.westerbergweather;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by eline on 08.04.2017.
 */

@Root(strict=false)
public class Pressure {

    @Attribute
    private String unit;
    @Attribute
    private double value;

    public String getUnit() {
        return unit;
    }

    public double getValue() {
        return Math.round(value); // Yr krever at vi runder av verdien før visning.
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

