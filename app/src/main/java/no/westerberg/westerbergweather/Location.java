package no.westerberg.westerbergweather;

import org.simpleframework.xml.Element;

/**
 * Created by Thomas Qvidahl on 03.05.2017.
 */

class Location {

    @Element
    private String name;
    @Element
    private String type;
    @Element
    private String country;
    @Element
    private String location; // This is a stringified geolocation lat/long ex "59.940 10.723"

    // Constructor
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

}
