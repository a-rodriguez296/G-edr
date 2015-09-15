package io.keepcoding.guedr.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;

import io.keepcoding.guedr.R;
import io.keepcoding.guedr.fragments.CityListFragment;
import io.keepcoding.guedr.fragments.CityPagerFragment;
import io.keepcoding.guedr.model.Cities;
import io.keepcoding.guedr.model.City;

/**
 * Created by arodriguez on 9/10/15.
 */
public class MainActivity extends AppCompatActivity implements CityListFragment.CityListListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        float density = metrics.density;
        int dpWidth = (int) (width /density);
        int dpHeight = (int) (height / density);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FragmentManager fm = getFragmentManager();

        //Primero hay que preguntar si existe el hueco para el fragment. Si existe, cargo el fragment

        if (findViewById(R.id.citylist) != null){
            if (fm.findFragmentById(R.id.citylist) == null){
                fm.beginTransaction()
                        .add(R.id.citylist, CityListFragment.newInstance())
                        .commit();
            }
        }


        if (findViewById(R.id.citypager) != null){
            if (fm.findFragmentById(R.id.citypager) == null){

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                int lastCityIndex = prefs.getInt(CityPagerFragment.PREF_LAST_CITY, 0);

                fm.beginTransaction()
                        .add(R.id.citypager, CityPagerFragment.newInstance(lastCityIndex))
                        .commit();
            }
        }


        FloatingActionButton addCityButton = (FloatingActionButton) findViewById(R.id.add_city_button);
        if (addCityButton != null){

            addCityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Cities cities = Cities.getInstance(MainActivity.this);
                    cities.addCity(String.format("Ciudad %d", cities.getCities().size() +1));
                }
            });
        }

    }


    @Override
    public void onCitySelected(City city, int index) {


/*        FragmentManager fm = getFragmentManager();

            fm.beginTransaction()
                    .replace(R.id.fragment, CityPagerFragment.newInstance(index))
                    //addToBackStack es como push. Es decir lo agrega a la pila
                    .addToBackStack(null)
                    .commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/


        //Averiguar si estoy en modo split screen
        if (findViewById(R.id.citypager) != null){

            //Caso split screen

            FragmentManager fm = getFragmentManager();
            CityPagerFragment cityPagerFragment = (CityPagerFragment) fm.findFragmentById(R.id.citypager);
            cityPagerFragment.goToCity(index);
        }
        else{

            //Caso no split screen
            Intent cityPagerIntent = new Intent(this, CityPagerActivity.class);
            cityPagerIntent.putExtra(CityPagerActivity.ARG_CITY_INDEX, index);
            startActivity(cityPagerIntent);
        }
    }


/*    @Override
    public void onBackPressed() {
        //Hay que quitar el super, pq este por defecto hace finish de la actividad
        FragmentManager fm = getFragmentManager();

        if (fm.getBackStackEntryCount()>0){
            fm.popBackStack();

            if (fm.getBackStackEntryCount()== 1){
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }


        }
        else{
            super.onBackPressed();
        }
    }*/


/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            FragmentManager fm = getFragmentManager();
            fm.popBackStack();

            if (fm.getBackStackEntryCount()== 1){
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }

            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }*/
}
