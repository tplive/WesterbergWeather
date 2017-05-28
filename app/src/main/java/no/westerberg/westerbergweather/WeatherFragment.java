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

import org.json.JSONObject;

import java.text.DateFormat;
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
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");

        //updateWeatherData(new CityPreference(getActivity()).getCity());

        // https://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
        new GetWeatherDataFromYr(new GetWeatherDataFromYr.AsyncResponse() {

            @Override
            public void processFinished(WeatherData output) {

                Location location = output.getLocation();
                String locString = location.getName();

                renderWeather2(output);
            }
        }).execute("http://www.yr.no/sted/Norge/Nordland/Rana/Mo_i_Rana/varsel.xml");


    }


    private void updateWeatherData(final String city) {
        //TODO: Make updateWeatherData get data from Yr.
        new Thread(){
            public void run() {
                final JSONObject json = RemoteFetch.getJSON(getActivity(), city);
                if (json == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();

    }

    private void renderWeather2(WeatherData weather) {

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


        //TODO Will render WeatherData object from Yr in the same way as the OWM version


    }

    private void renderWeather(JSONObject json) {
        //TODO: Need another method to render weather from Yr. Or class to convert to JSON
        try{
            cityField.setText(json.getString("name").toUpperCase(Locale.getDefault()) +
            ", " + json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            detailsField.setText(
                    details.getString("description").toUpperCase(Locale.getDefault()) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + "hPa"
            );

            currentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp")) + "˚C");

            DateFormat df = DateFormat.getDateInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText("Last updated: " + updatedOn);

            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        }catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {

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
        updateWeatherData(city);
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
