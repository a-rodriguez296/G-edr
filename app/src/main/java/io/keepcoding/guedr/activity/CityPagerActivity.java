package io.keepcoding.guedr.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import io.keepcoding.guedr.R;
import io.keepcoding.guedr.fragments.CityPagerFragment;

/**
 * Created by arodriguez on 9/13/15.
 */
public class CityPagerActivity  extends AppCompatActivity {


    public static final String ARG_CITY_INDEX ="cityIndex";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_pager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        int cityIndex = getIntent().getIntExtra(ARG_CITY_INDEX,0);


        FragmentManager fm = getFragmentManager();
        if(fm.findFragmentById(R.id.fragment)== null){
            fm.beginTransaction().
                    add(R.id.fragment, CityPagerFragment.newInstance(cityIndex)).
                    commit();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }

    }
}
