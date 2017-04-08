package no.westerberg.westerbergweather;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * Created by Eline Westerberg 08.04.2017.
 */

@Root(strict = false)
public class Time {

    @Attribute
    String from;
    @Attribute
    String to;
    @Element
    Symbol symbol;
    @Element
    WindDirection windDirection;
    @Element
    WindSpeed windSpeed;
    @Element
    Temperature temperature;
    @Element
    Pressure pressure;

    //TODO Getters and setters
}
