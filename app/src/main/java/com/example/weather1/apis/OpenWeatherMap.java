package com.example.weather1.apis;

import com.example.weather1.models.WeatherForecastResult;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Yassine Abou on 5/26/2021.
 */
public interface OpenWeatherMap {

    @GET("forecast")
    Observable<WeatherForecastResult> getForecastWeatherByLatLon(@Query("lat") String lat,
                                                                 @Query("lon") String lon,
                                                                 @Query("appid") String appid,
                                                                 @Query("units") String units);

    @GET("forecast")
    Observable<WeatherForecastResult> getForecastWeatherByCity(@Query("lat") String cityName,
                                                                 @Query("appid") String appid,
                                                                 @Query("units") String units);
}
