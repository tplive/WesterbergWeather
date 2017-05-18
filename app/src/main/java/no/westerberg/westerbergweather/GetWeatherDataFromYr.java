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

class GetWeatherDataFromYr extends AsyncTask<String, Void, WeatherData> {

    private final static String TAG = "GetWeatherDataFromYr";
    private Exception exception;

    @Override
    protected WeatherData doInBackground(String... urls) {

        Log.d(TAG, "Starting doInBackground...");

        try {
            String url = "http://www.yr.no/sted/Norge/Nordland/Rana/Mo/varsel.xml";

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

            ResponseEntity<WeatherData> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, WeatherData.class);

            if(responseEntity.getBody() != null) {
                return responseEntity.getBody();

            }else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(WeatherData weatherData) {
        super.onPostExecute(weatherData);
        Log.d(TAG, "onPostExecute");

    }
}
