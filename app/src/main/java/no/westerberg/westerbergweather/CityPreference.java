package no.westerberg.westerbergweather;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by thomasqvidahl on 18.05.2017.
 */

public class CityPreference {

    // SharedPreferences lagrer konfigurasjonsdata til fil
    //TODO Bygg ut klassen til å håndtere søkelogg.
    SharedPreferences prefs;

    public CityPreference(Activity activity) {

        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    // Hvis brukeren ikke har valgt by enda, sett standard
    // til Trondheim
    String getCity() {
        return prefs.getString("city", "Trondheim");
    }
    String getCityURL() {
        return prefs.getString("cityurl", "");
    }

    void setCity(String city) {

        prefs.edit().putString("city", city).apply();
    }

    void setCityURL(String city) {

        prefs.edit().putString("cityurl", city).apply();
    }
}
