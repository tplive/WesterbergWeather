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
import io.nlopez.smartlocation.SmartLocation;

// Lots of code from here: https://code.tutsplus.com/tutorials/create-a-weather-app-on-android--cms-21587

public class MainActivity extends AppCompatActivity implements WeatherFragment.OnFragmentInteractionListener {

    EditText searchBox;
    ImageButton searchBtn;

    // Location
    private static final int MY_PERMISSIONS_REQUEST_FOR_LOCATION = 1;


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

        // Location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Show explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.v("Location", "Show explanation");
            }else{

                // No explanation needed so we can request the permission

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FOR_LOCATION);
            }
        }else {

            findLocation();
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

    private String getAddressFromLocation(double lat, double lon) {

        String strAdd = "";
        Geocoder gcd = new Geocoder(MainActivity.this, Locale.getDefault());
        try {

            List<Address> addresses = gcd.getFromLocation(lat, lon, 1);
            if (addresses.size() < 0) {

                strAdd = addresses.get(0).getAddressLine(0);
            }
        }catch (Exception e) {
            Log.v("Location", "Geocoder crash");
        }

        return strAdd;
    }

    private void findLocation() {
        SmartLocation.with(this).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                getAddressFromLocation(lat, lon);

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


