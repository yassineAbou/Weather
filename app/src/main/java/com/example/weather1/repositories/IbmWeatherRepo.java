package com.example.weather1.repositories;

import com.example.weather1.apis.IbmWeather;
import com.example.weather1.models.CurrentWeather;
import com.example.weather1.models.Forecast12Hours;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yassine Abou on 6/11/2021.
 */
public class IbmWeatherRepo {


    private static final String BASE_URL = "https://api.weather.com/";
    private final IbmWeather mIbmWeather;
    private static IbmWeatherRepo INSTANCE;

    public IbmWeatherRepo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mIbmWeather = retrofit.create(IbmWeather.class);
    }

    public static IbmWeatherRepo getINSTANCE() {
        if (null == INSTANCE){
            INSTANCE = new IbmWeatherRepo();
        }
        return INSTANCE;
    }

   public Observable<CurrentWeather> getCurrentWeather(String geo, String unit, String lan, String form, String apiKey) {
        return mIbmWeather.getCurrentWeather(geo, unit, lan, form, apiKey);
   }

   public Observable<Forecast12Hours> getForecast12Hours(String geo, String lan, String form, String unit, String apiKey){
        return mIbmWeather.getForecast12Hours(geo, lan, form, unit, apiKey);
   }



}
