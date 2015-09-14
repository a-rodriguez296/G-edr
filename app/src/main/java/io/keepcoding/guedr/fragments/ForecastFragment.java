package io.keepcoding.guedr.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.keepcoding.guedr.R;
import io.keepcoding.guedr.activity.SettingsActivity;
import io.keepcoding.guedr.model.City;
import io.keepcoding.guedr.model.Forecast;

/**
 * Created by arodriguez on 9/9/15.
 */
public class ForecastFragment extends Fragment{

    private static final String ARG_CITY = "city";

    private Forecast mForecast;
    private City mCurrentCity;

    private ImageView mIcon;
    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mHumidity;
    private TextView mDescription;
    private TextView mCityName;


    private int mCurrentMetrics;

    //Método de clase para inicializar el el fragment con una ciudad
    public static Fragment newInstance(City city) {
        ForecastFragment fragment= new ForecastFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_CITY, city);
        fragment.setArguments(arguments);

        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments()!= null){
            mCurrentCity = (City) getArguments().getSerializable(ARG_CITY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);



        View root = inflater.inflate(R.layout.fragment_forecast, container, false);

        mMaxTemp = (TextView) root.findViewById(R.id.max_temp);
        mMinTemp = (TextView) root.findViewById(R.id.min_temp);
        mHumidity = (TextView) root.findViewById(R.id.humidity);
        mDescription = (TextView) root.findViewById(R.id.forecast_description);
        mIcon = (ImageView) root.findViewById(R.id.forecast_image);
        mCityName = (TextView) root.findViewById(R.id.city);





        setCity(mCurrentCity);

        setForecast(mCurrentCity.getmForecast());

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mCurrentMetrics = Integer.valueOf(pref.getString(getString(R.string.metric_selection), String.valueOf(SettingsActivity.PREF_CELSIUS)));


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        //comprobar si han cambiado las unidades

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //Forma de acceder al nombre del metric selection que esta en strings_activity_settings.xml
        int metrics = Integer.valueOf(pref.getString(getString(R.string.metric_selection), String.valueOf(SettingsActivity.PREF_CELSIUS)));

        if (mCurrentMetrics != metrics){
            final int previousMetrics = mCurrentMetrics;
            mCurrentMetrics = metrics;
            setForecast(mForecast);

            if (getView() != null){

                Snackbar.make(
                        getView(), R.string.updated_Preferences, Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                pref.edit().putString(getString(R.string.metric_selection), String.valueOf(previousMetrics))
                                        .apply();
                                mCurrentMetrics = previousMetrics;
                                setForecast(mForecast);
                            }
                        })
                        .show();
            }

        }
    }

    protected static float toFarenheit(float celsius){

        return (celsius *1.8f) +32f;

    }

    public void setCity(City city){
        mCityName.setText(city.getmName());
    }


    public void setForecast(Forecast forecast) {
        mForecast = forecast;

        float maxTemp = mCurrentMetrics == SettingsActivity.PREF_CELSIUS?
                forecast.getMaxTemp():toFarenheit(forecast.getMaxTemp());
        float minTemp = mCurrentMetrics ==  SettingsActivity.PREF_CELSIUS?
                forecast.getMinTemp():toFarenheit(forecast.getMinTemp());

        String metricString;
        if (mCurrentMetrics == SettingsActivity.PREF_CELSIUS){
            metricString = "ºC";
        }
        else{
            metricString = "ºF";
        }


        mMaxTemp.setText(String.format(getString(R.string.max_temp_parameter), maxTemp, metricString));
        mMinTemp.setText(String.format(getString(R.string.min_temp_parameter), minTemp, metricString));
        mHumidity.setText(String.format(getString(R.string.humidity_parameter), forecast.getHumidity()));
        mDescription.setText(forecast.getDescription());
        mCityName.setText(mCurrentCity.getmName());
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.forecast, menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(settingsIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
