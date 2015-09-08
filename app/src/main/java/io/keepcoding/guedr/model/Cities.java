package io.keepcoding.guedr.model;

import java.util.LinkedList;
import java.util.List;

public class Cities {
    private static Cities ourInstance = new Cities();

    private List<City> mCities;

    public static Cities getInstance() {
        return ourInstance;
    }

    private Cities() {
        mCities = new LinkedList<>();
        mCities.add(new City("Madrid", new Forecast(32, 19, 51, "Sol con algunas nubes", "icon01")));
        mCities.add(new City("Jaén", new Forecast(36, 23, 19, "Sol a tope", "icon02")));
        mCities.add(new City("Quito", new Forecast(30, 15, 40, "Sol con algunas nubes", "icon01")));
    }

    public List<City> getCities() {
        return mCities;
    }
}
