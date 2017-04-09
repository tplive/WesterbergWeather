package no.westerberg.westerbergweather;

import org.simpleframework.xml.Attribute;

/**
 * Created by eline on 08.04.2017.
 */

public class WindDirection {
    @Attribute
    private double deg;
    @Attribute
    private String code;
    @Attribute
    private String name;

    public void setCode(String code) {
        this.code = code;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDeg() {
        return deg;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
