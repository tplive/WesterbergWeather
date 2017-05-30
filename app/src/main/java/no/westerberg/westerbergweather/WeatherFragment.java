package no.westerberg.westerbergweather;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import java.util.Date;
import java.util.Locale;


public class WeatherFragment extends Fragment {

    Typeface weatherFont;

    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;

    Handler handler;


    private OnFragmentInteractionListener mListener;

    public WeatherFragment() {

        handler = new Handler();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Mapping av Yr sine værbeskrivelser til weatherFont. Yr sin symbolside er nede! :O
/*        404 Not Found
        The domain om.yr.no was not found on this server.

                All websites earlier on this server have been moved to a new server address.

                All website administrators have been notified by email about this change on the 11th of May, and reminded on 16th of May.*/

        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");

        // Hent currentCity fra MainActivity
        String currentCity = ((MainActivity)getActivity()).getCurrentCity();

        // Søk på Yr etter URL til currentCity
        searchCityNameYr(currentCity);

        // Oppdater værdata med currentCity fra preferences
        updateWeatherData(new CityPreference(getActivity()).getCity());

        // Kilde brukt underveis
        // https://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a


    }

    private void updateWeatherData(final String city) {

        // Starte async task for å hente værdata fra Yr
        new GetWeatherDataFromYr(new GetWeatherDataFromYr.AsyncResponse() {

            @Override
            public void processFinished(WeatherData output) {


                try{
                    // Finn bynavn fra værdataobjektet
                    Location location = output.getLocation();
                    String locString = location.getName();
                    Log.d("Location", locString);

                    // Send værdata til renderWeather
                    //TODO Rename metoden til renderWeather (erstatter OWM sin metode)
                    renderWeather2(output);
                }catch (Exception e) {
                    Log.d("Location", "Output object empty");
                    e.printStackTrace();
                }
            }
        }).execute(new CityPreference(getActivity()).getCityURL());

    }

    private void searchCityNameYr(final String city) {

        // Gjør et async søk (returnerer JSON array) etter bynavn og bygger Yr-URL for å hente værdata
        new Thread(){
            public void run() {
                final JSONArray json = RemoteFetch.getJSON(getActivity(), city);
                if (json == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), String.format("City %s not found", city),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            // Sett city URL i preferences.
                            new CityPreference(getActivity()).setCityURL(getCityURLFromJSON(json));
                            updateWeatherData(new CityPreference(getActivity()).getCity());
                        }
                    });
                }
            }
        }.start();

    }

    private void renderWeather2(WeatherData weather) {

        // Oppdatere UI med værdata fra Yr

        String strCity = String.format(Locale.getDefault(), "%s, %s",
                weather.getLocation().getName().toUpperCase(Locale.getDefault()),
                weather.getLocation().getCountry().toUpperCase(Locale.getDefault()));
        String strTemperature = String.format(Locale.getDefault(), "%s ˚C", weather.getForecast().getTimeList().get(0).getTemperature().getValue());
        String strPressUnit = weather.getForecast().getTimeList().get(0).getPressure().getUnit();
        double dblPressure = weather.getForecast().getTimeList().get(0).getPressure().getValue();
        double dblWindspeed = weather.getForecast().getTimeList().get(0).getWindSpeed().getMps();
        String strWindName = weather.getForecast().getTimeList().get(0).getWindDirection().getName();
        String strWeatherName = weather.getForecast().getTimeList().get(0).getSymbol().getName();
        cityField.setText(strCity);
        currentTemperatureField.setText(strTemperature);
        detailsField.setText( String.format(Locale.getDefault(),
                "%s \n Lufttrykk: %s %s \n Vind: %s m/s %s", strWeatherName, dblPressure, strPressUnit, dblWindspeed, strWindName));

    }

    private String getCityURLFromJSON(JSONArray json) {

        // Med JSON data fra Yr, hent ut det første stedet og lag en gyldig værdata URL
        // TODO Ta alle search-suggestions og send fortløpende til UI, la bruker velge mellom alle treff.
        try{
            String cityURL = json.getJSONArray(1).getJSONArray(0).get(1).toString();

            cityURL = "http://www.yr.no" + cityURL + "varsel.xml";
            Log.i("JSON", cityURL);

            return cityURL;

        }catch (Exception e) {
            // Hvis bruker søker etter et sted som ikke finnes, blir det krøll.
            // TODO Gi en bedre melding til bruker om at søket ikke finnes.

            Log.e("JSON", "One or more fields not found in the JSON data: " + json.toString());
            e.printStackTrace();
            return null;
        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {


        //TODO Skriv om metoden så den mapper Yr sine værikoner til weatherFont ikonene.
        int id = actualId / 100;
        String icon = "";
        if (actualId==800) {
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            }else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        }else {
            switch(id) {
                case 2 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3 : icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7 : icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8 : icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 6 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5 : icon = getActivity().getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherIcon.setText(icon);
    }

    public void changeCity(String city) {

        // Bytt by, lagrer i SharedPrefs
        searchCityNameYr(city);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        cityField = (TextView) rootView.findViewById(R.id.cityField);
        updatedField = (TextView) rootView.findViewById(R.id.updatedField);
        detailsField = (TextView) rootView.findViewById(R.id.detailsField);
        currentTemperatureField = (TextView) rootView.findViewById(R.id.temperatureField);
        weatherIcon = (TextView) rootView.findViewById(R.id.weatherIconField);

        weatherIcon.setTypeface(weatherFont);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
