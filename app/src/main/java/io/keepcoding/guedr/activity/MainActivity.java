package io.keepcoding.guedr.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
                    .replace(R.id.fragment, CityPagerFragment.newInstance())
                    .commit();




    }
}
