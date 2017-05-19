package no.westerberg.westerbergweather;

import android.location.Address;

import java.util.List;

/**
 * Created by THPA on 19.05.2017.
 */

public abstract class OnGeoCoderFinishedListener {
    public abstract void onFinished(List<Address> results);
}
