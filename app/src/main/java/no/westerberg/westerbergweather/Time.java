package no.westerberg.westerbergweather;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * Created by Eline Westerberg 08.04.2017.
 */

@Root(strict = false)
class Time {

    @Attribute
    private String from;
    @Attribute
    private String to;
    @Attribute
    private int period;
    @Element
    private Symbol symbol;
    @Element
    private WindDirection windDirection;
    @Element
    private WindSpeed windSpeed;
    @Element
    private Temperature temperature;
    @Element
    private Pressure pressure;

    public String getFrom() { return from;   }
    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public Symbol getSymbol() {
        return symbol;
    }
    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public WindDirection getWindDirection() {
        return windDirection;
    }
    public void setWindDirection(WindDirection windDirection) {
        this.windDirection = windDirection;
    }

    public WindSpeed getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(WindSpeed windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Pressure getPressure() {
        return pressure;
    }
    public void setPressure(Pressure pressure) {
        this.pressure = pressure;
    }

    public Temperature getTemperature() {
        return temperature;
    }
    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

}
