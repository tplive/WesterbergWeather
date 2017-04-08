import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created  by Eline Westerberg on 08.04.2017.
 */
@Root(strict = false)
public class WeatherData {

    @Element
    Forecast forecast;

    //TODO Getters and setters
}

