package io.keepcoding.guedr.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import io.keepcoding.guedr.R;
import io.keepcoding.guedr.model.Forecast;


public class ForecastActivity extends AppCompatActivity {

    private Forecast mForecast;

    private ImageView mIcon;
    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mHumidity;
    private TextView mDescription;

    private int mCurrentMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mMaxTemp = (TextView) findViewById(R.id.max_temp);
        mMinTemp = (TextView) findViewById(R.id.min_temp);
        mHumidity = (TextView) findViewById(R.id.humidity);
        mDescription = (TextView) findViewById(R.id.forecast_description);
        mIcon = (ImageView) findViewById(R.id.forecast_image);

        setForecast(new Forecast(30, 15, 25, "Algunas nubes", "ico01"));

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        mCurrentMetrics = Integer.valueOf(pref.getString(getString(R.string.metric_selection), String.valueOf(SettingsActivity.PREF_CELSIUS)));

    }

    @Override
    protected void onResume() {
        super.onResume();

        //comprobar si han cambiado las unidades

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        //Forma de acceder al nombre del metric selection que esta en strings_activity_settings.xml
        int metrics = Integer.valueOf(pref.getString(getString(R.string.metric_selection), String.valueOf(SettingsActivity.PREF_CELSIUS)));

        if (mCurrentMetrics != metrics){
            mCurrentMetrics = metrics;
            setForecast(mForecast);
        }



    }

    protected static float toFarenheit(float celsius){

        return (celsius *1.8f) +32f;

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


        mMaxTemp.setText(String.format(getString(R.string.max_temp_parameter), maxTemp)+ metricString);
        mMinTemp.setText(String.format(getString(R.string.min_temp_parameter), minTemp)+ metricString);
        mHumidity.setText(String.format(getString(R.string.humidity_parameter), forecast.getHumidity()));
        mDescription.setText(forecast.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forecast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
