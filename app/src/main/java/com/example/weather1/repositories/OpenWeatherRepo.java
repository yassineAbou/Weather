package com.example.weather1.repositories;

import com.example.weather1.apis.OpenWeatherMap;
import com.example.weather1.models.WeatherForecastResult;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yassine Abou on 6/5/2021.
 */
public class OpenWeatherRepo {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private final OpenWeatherMap mWeatherMap;
    private static OpenWeatherRepo INSTANCE;

    public OpenWeatherRepo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mWeatherMap = retrofit.create(OpenWeatherMap.class);
    }

    public static OpenWeatherRepo getINSTANCE() {
        if (null == INSTANCE){
            INSTANCE = new OpenWeatherRepo();
        }
        return INSTANCE;
    }

    public Observable<WeatherForecastResult> getForecast5Days(String lat, String lon, String appId, String metric) {
        return mWeatherMap.getForecastWeatherByLatLon(lat, lon, appId, metric);
    }
}
