package com.example.weather1.models;

/**
 * Created by Yassine Abou on 5/25/2021.
 */
public class Weather {

    private double id;
    private String main;
    private String description;
    private String icon;

    public Weather() { }

    //--Getters
    public double getId() { return id; }
    public String getMain() { return main; }
    public String getDescription() { return description; }
    public String getIcon() { return icon; }

    //--Setters

    public void setId(double id) { this.id = id; }
    public void setMain(String main) { this.main = main; }
    public void setDescription(String description) { this.description = description; }
    public void setIcon(String icon) { this.icon = icon; }
}
