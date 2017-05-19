package no.westerberg.westerbergweather;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

// Lots of code from here: https://code.tutsplus.com/tutorials/create-a-weather-app-on-android--cms-21587

public class MainActivity extends AppCompatActivity implements WeatherFragment.OnFragmentInteractionListener {

    EditText searchBox;
    ImageButton searchBtn;

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
        searchBox = (EditText)findViewById(R.id.searchBox);
        searchBtn = (ImageButton)findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.v("onClick", "Clicked Search button");
                        changeCity(searchBox.getText().toString());
                    }
                }
        );

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "onFragmentInteraction");
    }

    public void changeCity(String city) {
        WeatherFragment wf = (WeatherFragment)getSupportFragmentManager()
                .findFragmentById(R.id.currentWeatherFragment);
        wf.changeCity(city);
        new CityPreference(this).setCity(city);
    }


}


