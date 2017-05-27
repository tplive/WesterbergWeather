package no.westerberg.westerbergweather;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * Created by Eline Westerberg 08.04.2017.
 */

@Root(strict = false)
class Time {


    @Attribute(required = false)
    private String from;
    @Attribute(required = false)
    private String to;
    @Element(required = false)
    private Symbol symbol;
    @Element(required = false)
    private WindDirection windDirection;
    @Element(required = false)
    private WindSpeed windSpeed;
    @Element(required = false)
    private Temperature temperature;
    @Element(required = false)
    private Pressure pressure;
    @Element(required = false)
    String title;
    @Element(required = false)
    String body;

    @Attribute(required = false)
    int period;


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

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
