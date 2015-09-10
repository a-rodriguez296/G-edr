package io.keepcoding.guedr.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by arodriguez on 9/10/15.
 */
public class Cities {

    private static Cities ourInstance;


    private List<City> mCities;

    public static Cities getInstance(){

        if (ourInstance == null){
            ourInstance = new Cities();
        }
        return ourInstance;
    }

    private Cities(){

        mCities = new LinkedList<>();
        mCities.add(new City("Bogotá", new Forecast(33,25,14, "mensaje", "icon01")));
        mCities.add(new City("Cali", new Forecast(20,10,50, "mensaje", "icon01")));
        mCities.add(new City("Medellín", new Forecast(20,10,50, "mensaje", "icon01")));
        mCities.add(new City("Barranquilla", new Forecast(20,10,50, "mensaje", "icon01")));
    }


    public List<City> getCities() {
        return mCities;
    }

    public City cityAtPosition(int position){
        return mCities.get(position);

    }
}
