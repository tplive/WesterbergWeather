package no.westerberg.westerbergweather;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import layout.WeatherFragment;

public class MainActivity extends AppCompatActivity implements WeatherFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.currentWeatherFragment, new WeatherFragment())
                    .commit();
        }


        new GetWeatherDataFromYr().execute("http://www.yr.no/sted/Norge/Nordland/Rana/Mo/varsel.xml");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
