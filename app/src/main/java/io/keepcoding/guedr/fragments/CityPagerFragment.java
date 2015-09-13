package io.keepcoding.guedr.fragments;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.keepcoding.guedr.R;
import io.keepcoding.guedr.model.Cities;

/**
 * Created by arodriguez on 9/10/15.
 */
public class CityPagerFragment  extends Fragment{

    private Cities mCities;
    
    private ViewPager mPager;

    public static CityPagerFragment newInstance() {

        return new CityPagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Para los fragments acá solo se especifica si hay optionsMenu y se hace catch de los argumentos como en
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mCities = Cities.getInstance();

        //fragment_city_pager es un contenedor aka fragment vacío
        View root = inflater.inflate(R.layout.fragment_city_pager, container, false);
        mPager = (ViewPager) root.findViewById(R.id.view_pager);
        mPager.setAdapter(new CityPagerAdapter(getFragmentManager()));
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                updateCityInformation(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        updateCityInformation(mPager.getCurrentItem());

        return root;
    }

    protected void updateCityInformation(){
        updateCityInformation(mPager.getCurrentItem());

    }


    protected void updateCityInformation(int position){
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(mCities.getCities().get(position).getmName());
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.citypager, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.previous){
            mPager.setCurrentItem(mPager.getCurrentItem()-1);
        }
        else{
            mPager.setCurrentItem(mPager.getCurrentItem()+1);
        }
        updateCityInformation();
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (mPager!= null){

            MenuItem previous = menu.findItem(R.id.previous);
            MenuItem next = menu.findItem(R.id.next);

            previous.setEnabled(mPager.getCurrentItem()>0);
            next.setEnabled(mPager.getCurrentItem()<mCities.getCities().size()-1);

        }

    }

    protected class CityPagerAdapter extends FragmentPagerAdapter {


        private Cities mCities;

        public CityPagerAdapter(FragmentManager fm) {
            super(fm);
            mCities = Cities.getInstance();
        }

        @Override
        public Fragment getItem(int i) {
            return ForecastFragment.newInstance(mCities.cityAtPosition(i));
        }

        @Override
        public int getCount() {
            return mCities.getCities().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCities.cityAtPosition(position).getmName();
        }
    }

}
