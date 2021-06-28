package com.example.weather1.viewmodels;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather1.models.CurrentWeather;
import com.example.weather1.repositories.IbmWeatherRepo;
import com.example.weather1.utils.Common;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by Yassine Abou on 6/5/2021.
 */
public class CurrentWeatherVM extends ViewModel {


     public MutableLiveData<CurrentWeather> postsMutableLiveData = new MutableLiveData<>();
     private final CompositeDisposable mDisposables = new CompositeDisposable();
     private final Double lat = Common.currentLocation.getLatitude();
     private final Double lon = Common.currentLocation.getLongitude();
     private final List<Double> data = Arrays.asList(lat, lon);

    public void getCurrentWeather() {
        Observable<CurrentWeather> observable = IbmWeatherRepo.getINSTANCE().getCurrentWeather(
                TextUtils.join(",", data),
                "m",
                "en-US",
                "json",
                Common.IBM_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable d = observable.subscribeWith(new DisposableObserver<CurrentWeather>() {
            @Override
            public void onNext(@NonNull CurrentWeather currentWeather) {
                postsMutableLiveData.setValue(currentWeather);
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
