package com.example.weather1.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather1.models.WeatherForecastResult;
import com.example.weather1.repositories.OpenWeatherRepo;
import com.example.weather1.utils.Common;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * Created by Yassine Abou on 6/10/2021.
 */
public class Next5DaysVM extends ViewModel {


    public MutableLiveData<WeatherForecastResult> postsMutableLiveData = new MutableLiveData<>();
    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public void getNext5Days() {


        Observable<WeatherForecastResult> observable = OpenWeatherRepo.getINSTANCE().getForecast5Days(
        String.valueOf(Common.currentLocation.getLatitude()),
                String.valueOf(Common.currentLocation.getLongitude()),
                Common.OPENWEATHERMAP_ID,
                "metric" )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable d = observable.subscribeWith(new DisposableObserver<WeatherForecastResult>() {
            @Override
            public void onNext(@NonNull WeatherForecastResult weatherForecastResult) {
                postsMutableLiveData.setValue(weatherForecastResult);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        mDisposables.add(d);


    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposables.clear();
    }







}
