package no.westerberg.westerbergweather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;



public class MainActivity extends AppCompatActivity implements WeatherFragment.OnFragmentInteractionListener {

    EditText searchBox;
    ImageButton searchBtn;

    LocationManager mLocationManager;
    LocationListener mLocationListener;
    String currentCity = "Unknown city";

    public String getCurrentCity() {
        // Returnere currentCity, brukes av WeatherFragment
        return currentCity;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                }
            }
        }
    }

    //Ved forespørsel om rettigheter, brukes for å tillate Location
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

        // Henter lokasjonstjeneste
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Setter opp listener på lokasjon
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.i(TAG, "Location changed..");
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        // Sjekk om vi har rettigheter til å bruke "fine location"
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {

            mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, 10, 10, mLocationListener);

            // Last known location
            Location lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            // Sjekke at vi har en lastKnownLocation som kan brukes
            double lat;
            double lon;
            if (lastKnownLocation != null) {
                lat = lastKnownLocation.getLatitude();
                lon = lastKnownLocation.getLongitude();
            }else {
                // Hvis ikke, sett bogus koordinater
                Log.d("Position", "lastKnownLocation is null");
                lat = 1.00;
                lon = 50.00;
            }

            // Get address (City) from position
            currentCity = getAddressFromLocation(lat, lon);

            Toast.makeText(this, "Current city: " + currentCity, Toast.LENGTH_SHORT).show();

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

        // For testing, sett tydelig feil by i UI (og hvor den kommer fra)
        String city = "Unknown city (getAddressFromLocation)";

        // Start geocoder for å finne navn fra posisjon
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        // Vi vil ha bynavn fra Locality. Men Locality returnerer ofte "null". Derfor henter vi 10 resultater fra posisjonen
        // og looper gjennom dem og tar det første som ikke er null. Hacky fun! :D

        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 10);

            if (addresses != null && addresses.size() > 0) {

                Log.i("Geocoder", addresses.toString());

                for (Address adr : addresses) {
                    if (adr.getLocality() != null && adr.getLocality().length() > 0 ) {
                        city = adr.getLocality();
                    }
                }

            }

        }catch (IOException e){
            e.printStackTrace();
        }

        return city;

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

        //TODO Ved klikk i fragment, hent opp langtidsvarsel for stedet
        Log.d(TAG, "onFragmentInteraction");
    }

    public void changeCity(String city) {
        WeatherFragment wf = (WeatherFragment)getSupportFragmentManager()
                .findFragmentById(R.id.currentWeatherFragment);
        wf.changeCity(city);
        new CityPreference(this).setCity(city);
    }


}


