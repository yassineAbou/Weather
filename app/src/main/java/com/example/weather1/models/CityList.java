package com.example.weather1.models;

/**
 * Created by Yassine Abou on 6/21/2021.
 */
public class CityList {

    private int id;
    private String name,Country;
    private Coord mCoord;

    public CityList() { }

    //Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCountry() { return Country; }
    public Coord getCoord() { return mCoord; }

    //Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCountry(String country) { Country = country; }
    public void setCoord(Coord coord) { mCoord = coord; }
}
