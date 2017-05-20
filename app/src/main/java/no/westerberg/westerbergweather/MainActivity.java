package no.westerberg.westerbergweather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;
import java.util.Locale;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.OnReverseGeocodingListener;
import io.nlopez.smartlocation.SmartLocation;

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

        SmartLocation.with(this).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {

                double lat = location.getLatitude();
                double lon = location.getLongitude();

                Log.v("Location", "Position: " + lat + lon );

                getAddressFromLocation(lat, lon);



            }
        });

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

    private String getAddressFromLocation(double lat, double lon) {

        String strAddress = "";

        SmartLocation.with(this).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {

                SmartLocation.with(MainActivity.this).geocoding()
                        .reverse(location, new OnReverseGeocodingListener() {
                            @Override
                            public void onAddressResolved(Location location, List<Address> list) {

                                if (list.size() < 0) {

                                    final String address = list.get(0).getAddressLine(0);
                                    final String city = list.get(0).getLocality();
                                    final String state = list.get(0).getAdminArea();
                                    final String country = list.get(0).getCountryName();
                                    final String postalCode = list.get(0).getPostalCode();
                                    Log.d("Location", address + city + state + country + postalCode);
                                    //strAddress = address + city + state + country + postalCode;
                                }
                            }
                        });

            }
        });
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


