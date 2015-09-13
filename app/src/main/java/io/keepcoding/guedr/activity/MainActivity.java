package io.keepcoding.guedr.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import io.keepcoding.guedr.R;
import io.keepcoding.guedr.fragments.CityListFragment;
import io.keepcoding.guedr.fragments.CityPagerFragment;
import io.keepcoding.guedr.model.City;

/**
 * Created by arodriguez on 9/10/15.
 */
public class MainActivity extends AppCompatActivity implements CityListFragment.CityListListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.fragment) == null){
            fm.beginTransaction()
                    .add(R.id.fragment, CityListFragment.newInstance())
                    .commit();
        }
    }


    @Override
    public void onCitySelected(City city, int index) {


        FragmentManager fm = getFragmentManager();

            fm.beginTransaction()
                    .replace(R.id.fragment, CityPagerFragment.newInstance(index))
                    //addToBackStack es como push. Es decir lo agrega a la pila
                    .addToBackStack(null)
                    .commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
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
    }


    @Override
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
    }
}
