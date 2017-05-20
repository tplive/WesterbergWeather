package no.westerberg.westerbergweather;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutCompat;

import java.net.URL;

/**
 * Created by thomasqvidahl on 19.05.2017.
 */

public class LocationAsyncTask extends AsyncTask<URL, Integer, Long> {


    @Override
    protected Long doInBackground(URL... params) {

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
    }
}
