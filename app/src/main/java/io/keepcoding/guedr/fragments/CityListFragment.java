package io.keepcoding.guedr.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    private CityListListener mListener;
    private CityBroadcastReceiver mBroadcastReceiver;


    public static CityListFragment newInstance(){

        return new CityListFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_city_list, container, false);

        mCities = Cities.getInstance(getActivity());
        ListView listView = (ListView) root.findViewById(android.R.id.list);
        ArrayAdapter<City> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1, //Layout de la celda
                mCities.getCities());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mListener != null) {
                    mListener.onCitySelected(mCities.getCities().get(i), i);
                }
            }
        });
        mBroadcastReceiver= new CityBroadcastReceiver(adapter);
        getActivity().
                registerReceiver(mBroadcastReceiver,
                        //Filtro para que solo escuche este broadcast
                        new IntentFilter(Cities.CITY_LIST_CHANGED_ACTION));



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (CityListListener) activity;


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (CityListListener) getActivity();

    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;

    }

    public interface CityListListener{

        void onCitySelected(City city, int index);

    }


    private class CityBroadcastReceiver extends BroadcastReceiver{


        private ArrayAdapter mAdapter;


        public CityBroadcastReceiver(ArrayAdapter adapter){
            super();
            mAdapter = adapter;

        }

        @Override
        public void onReceive(Context context, Intent intent) {

            //Esto es como un reloadData
            mAdapter.notifyDataSetChanged();
        }
    }

}
