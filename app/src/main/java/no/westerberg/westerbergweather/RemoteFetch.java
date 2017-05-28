package no.westerberg.westerbergweather;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by thomasqvidahl on 18.05.2017.
 */

class RemoteFetch {

    private final static String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private final static String YR_WEBSOK_URL = "http://www.yr.no/_/websvc/jsonforslagsboks.aspx?s=%s";

    public static JSONArray getJSON(Context context, String city) {

        try {
            URL url = new URL(String.format(YR_WEBSOK_URL, city));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while( ( tmp = reader.readLine() ) != null ) {
                json.append(tmp).append("\n");
            }
            reader.close();

            JSONArray data = new JSONArray(json.toString());

            // 404 hvis ikke suksess
            //TODO Test om ikke suksess. Return null hvis feil.

            return data;


        }catch (Exception e) {
            return null;
        }
    }
}
