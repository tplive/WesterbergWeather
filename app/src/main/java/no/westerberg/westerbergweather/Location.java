package no.westerberg.westerbergweather;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Thomas Qvidahl on 03.05.2017.
 * Forenklet location data fra Yr
 */
@Root(strict=false)
class Location {

    @Element
    private String name;
    @Element
    private String type;
    @Element
    private String country;
    @Element
    private String location; // TODO This will be a stringified geolocation lat/long ex "59.940 10.723"

    // Constructor
    // Vi setter hele objektet i en operasjon
    public void Location(String name, String type, String country, String location) {
        this.name = name;
        this.type = type;
        this.country = country;
        this.location = location;
    }
    public String getName() { return name; }
    public String getType() { return type;  }
    public String getCountry() { return country;  }
    public String getLocation() { return location; }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}
