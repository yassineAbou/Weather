package com.example.weather1.models;

/**
 * Created by Yassine Abou on 5/25/2021.
 */
public class Sys {

    private String country;
    private double sunrise;
    private double sunset;

    public Sys() { }

    //--Getters
    public String getCountry() { return country; }
    public double getSunrise() { return sunrise; }
    public double getSunset() { return sunset; }

    //--Setters
    public void setCountry(String country) { this.country = country; }
    public void setSunrise(double sunrise) { this.sunrise = sunrise; }
    public void setSunset(double sunset) { this.sunset = sunset; }
}
