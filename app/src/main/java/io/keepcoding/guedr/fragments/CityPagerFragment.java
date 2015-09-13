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
import android.view.View;
import android.view.ViewGroup;

import io.keepcoding.guedr.R;
import io.keepcoding.guedr.model.Cities;

/**
 * Created by arodriguez on 9/10/15.
 */
public class CityPagerFragment  extends Fragment{

    private Cities mCities;

    public static CityPagerFragment newInstance() {

        return new CityPagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mCities = Cities.getInstance();

        //fragment_city_pager es un contenedor aka fragment vacío
        View root = inflater.inflate(R.layout.fragment_city_pager, container, false);
        ViewPager pager = (ViewPager) root.findViewById(R.id.view_pager);
        pager.setAdapter(new CityPagerAdapter(getFragmentManager()));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        updateCityInformation(pager.getCurrentItem());

        return root;
    }


    protected void updateCityInformation(int position){
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(mCities.getCities().get(position).getmName());
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