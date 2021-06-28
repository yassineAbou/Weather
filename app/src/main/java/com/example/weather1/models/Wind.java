package com.example.weather1.models;

/**
 * Created by Yassine Abou on 5/25/2021.
 */
public class Wind {

    private double speed;
    private double deg;
    private double gust;

    public Wind() { }

    //--Getters
    public double getSpeed() { return speed; }
    public double getDeg() { return deg; }
    public double getGust() { return gust; }

    //--Setters
    public void setSpeed(double speed) { this.speed = speed; }
    public void setDeg(double deg) { this.deg = deg; }
    public void setGust(double gust) { this.gust = gust; }
}
