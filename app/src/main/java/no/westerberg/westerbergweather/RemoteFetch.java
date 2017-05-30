package no.westerberg.westerbergweather;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by thomasqvidahl on 18.05.2017.
 * Klassen er omskrevet fra å bruke Open Weather Map til å bruke Yr, derfor kan det henge igjen
 * noe kode som tilhører OWM.. :)
 */

class RemoteFetch {

    // Husk! OWM krever en API nøkkel i header!
    private final static String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";

    // Takk til medstudentene som "fisket ut" Yr sin webservice for å søke etter stedsnavn og lage Yr-URL av! :D
    private final static String YR_WEBSOK_URL = "http://www.yr.no/_/websvc/jsonforslagsboks.aspx?s=%s";

    public static JSONArray getJSON(Context context, String city) {


        try {
            // Bygge URL som sendes til webservice
            URI uri = new URI("http", null, "www.yr.no", -1, "/_/websvc/jsonforslagsboks.aspx", String.format("s=%s", city), null);

            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while( ( tmp = reader.readLine() ) != null ) {
                json.append(tmp).append("\n");
            }
            reader.close();

            // JSON Array starter og stopper med [ og slutter med ], ikke {}
            JSONArray data = new JSONArray(json.toString());

            // 404 hvis ikke suksess
            //TODO Test om ikke suksess. Return null hvis feil.

            return data;


        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
