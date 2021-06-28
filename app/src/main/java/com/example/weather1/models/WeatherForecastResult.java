package com.example.weather1.models;

import java.util.List;

/**
 * Created by Yassine Abou on 6/6/2021.
 */
public class WeatherForecastResult {

    public String cod;
    public double message;
    public double cnt;
    public List<MyList> list;
    public City city;
}
