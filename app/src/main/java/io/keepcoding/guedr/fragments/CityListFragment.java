package io.keepcoding.guedr.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import io.keepcoding.guedr.R;
import io.keepcoding.guedr.model.Cities;
import io.keepcoding.guedr.model.City;

/**
 * Created by arodriguez on 9/13/15.
 */
public class CityListFragment extends Fragment {


    private Cities mCities;



    public static CityListFragment newInstance(){

        return new CityListFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_city_list, container, false);

        mCities = Cities.getInstance();
        ListView listView = (ListView) root.findViewById(android.R.id.list);
        ArrayAdapter<City> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mCities.getCities());
        listView.setAdapter(adapter);

        return root;
    }

}
