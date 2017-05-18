package no.westerberg.westerbergweather;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

// Lots of code from here: https://code.tutsplus.com/tutorials/create-a-weather-app-on-android--cms-21587

public class MainActivity extends AppCompatActivity implements WeatherFragment.OnFragmentInteractionListener {

    private final static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.currentWeatherFragment, new WeatherFragment())
                    .commit();
        }

        Log.d(TAG, "About to execute GetWeatherDataFromYr asyncTask...");
        new GetWeatherDataFromYr().execute("http://www.yr.no/sted/Norge/Nordland/Rana/Mo/varsel.xml");

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "onFragmentInteraction");
    }
}
