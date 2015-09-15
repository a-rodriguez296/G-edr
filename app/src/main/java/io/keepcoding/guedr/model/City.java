package io.keepcoding.guedr.model;

import java.io.Serializable;

/**
 * Created by arodriguez on 9/10/15.
 */
public class City implements Serializable{

    private String mName;
    private Forecast mForecast;

    public City(String mName, Forecast mForecast) {
        this.mName = mName;
        this.mForecast = mForecast;
    }

    public City(String mName){
        this(mName, new Forecast(30,15,20,"Sol con algunas nubes", "ico01"));
    }


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Forecast getmForecast() {
        return mForecast;
    }

    public void setmForecast(Forecast mForecast) {
        this.mForecast = mForecast;
    }

    @Override
    public String toString() {
        return mName;
    }
}
