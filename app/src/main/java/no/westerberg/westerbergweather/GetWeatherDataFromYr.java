package no.westerberg.westerbergweather;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created  by Thomas Qvidahl on 08.04.2017.
 */

public class GetWeatherDataFromYr extends AsyncTask<String, Void, WeatherData> {

    // Asynkron funksjon som henter data fra yr.no
    public interface AsyncResponse {
        void processFinished(WeatherData output);
    }

    public AsyncResponse delegate = null;

    public GetWeatherDataFromYr(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    private final static String TAG = "GetWeatherDataFromYr";
    private Exception exception;

    @Override
    protected WeatherData doInBackground(String... urls) {

        String url = urls[0];

        Log.d(TAG, "Starting doInBackground...");

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

            ResponseEntity<WeatherData> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, WeatherData.class);

            WeatherData weatherData = responseEntity.getBody();

            // Returnere komplett WeatherData objekt.
            return weatherData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(WeatherData output) {
        super.onPostExecute(output);

        delegate.processFinished(output);

    }
}
