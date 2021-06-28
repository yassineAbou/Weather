package com.example.weather1.apis;

import com.example.weather1.models.Forecast12Hours;
import com.example.weather1.models.CurrentWeather;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Yassine Abou on 6/11/2021.
 */
public interface IbmWeather {



    @GET("v3/wx/forecast/hourly/12hour/enterprise")
    Observable<Forecast12Hours> getForecast12Hours(@Query(value = "geocode", encoded = true) String geo,
                                                   @Query("language") String lan,
                                                   @Query("format") String form,
                                                   @Query("units") String units,
                                                   @Query("apiKey") String apiKey);

    @GET("v3/wx/observations/current")
    Observable<CurrentWeather> getCurrentWeather(@Query(value = "geocode", encoded = true) String geo,
                                                 @Query("units") String units,
                                                 @Query("language") String lan,
                                                 @Query("format") String form,
                                                 @Query("apiKey") String apiKey);


}
