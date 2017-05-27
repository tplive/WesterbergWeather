package no.westerberg.westerbergweather;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Thomas Qvidahl on 03.05.2017.
 * Forenklet location data fra Yr
 */
@Root(strict=false)
class Location {

    @Element(required = false)
    private String name;
    @Element(required = false)
    private String type;
    @Element(required = false)
    private String country;
    @Element(required = false)
    private String location; // TODO This will be a stringified geolocation lat/long ex "59.940 10.723"

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
