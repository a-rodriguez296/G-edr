package io.keepcoding.guedr.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by arodriguez on 9/10/15.
 */
public class Cities {


    private static final String PREF_CITIES = "prefCities";

    private WeakReference<Context> mContext;

    private static Cities ourInstance;


    private List<City> mCities;

    public static Cities getInstance(Context context){

        if (ourInstance == null || ourInstance.mContext.get() == null){

            //En el parametro se puede usar cualquier objeto. Normalmente se utliza un static final
            synchronized (PREF_CITIES){
                if (ourInstance == null){
                    ourInstance = new Cities(context);
                }
                else if(ourInstance.mContext.get() == null){
                    ourInstance.mContext = new WeakReference<>(context);
                }
            }
        }
        return ourInstance;
    }

    private Cities(Context context){

        mCities = new LinkedList<>();
        mContext =  new WeakReference<>(context) ;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);


        Set<String> cities = prefs.getStringSet(PREF_CITIES, new HashSet<String>());


        for (String city: cities){
            mCities.add(new City(city, new Forecast(30,15,20,"Sol con algunas nubes", "ico01")));
        }

        /*mCities.add(new City("Bogotá", new Forecast(33,25,14, "mensaje", "icon01")));
        mCities.add(new City("Cali", new Forecast(20,10,50, "mensaje", "icon01")));
        mCities.add(new City("Medellín", new Forecast(20,10,50, "mensaje", "icon01")));
        mCities.add(new City("Barranquilla", new Forecast(20,10,50, "mensaje", "icon01")));*/
    }

    public void save(){

        synchronized (PREF_CITIES){
            if (mContext.get() != null){
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext.get());

                Set<String> citiesSet = new HashSet<>();
                for (City city: mCities){
                    citiesSet.add(city.getmName());
                }

                prefs.edit().
                        putStringSet(PREF_CITIES, citiesSet).
                        apply();
            }
        }
    }

    public void addCity(String cityName){

        mCities.add(new City(cityName));
        save();
    }


    public List<City> getCities() {
        return mCities;
    }

    public City cityAtPosition(int position){
        return mCities.get(position);

    }
}
