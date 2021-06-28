package com.example.weather1.viewmodels;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather1.models.Forecast12Hours;
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
 * Created by Yassine Abou on 6/11/2021.
 */
public class Next12HoursVM extends ViewModel {

    public MutableLiveData<Forecast12Hours> postsMutableLiveData = new MutableLiveData<>();
    private final CompositeDisposable mDisposables = new CompositeDisposable();
    private final Double lat = Common.currentLocation.getLatitude();
    private final Double lon = Common.currentLocation.getLongitude();
    private final List<Double> data = Arrays.asList(lat, lon);

    public void getNext12Hours() {

        Observable<Forecast12Hours> observable = IbmWeatherRepo.getINSTANCE().getForecast12Hours(
                TextUtils.join(",", data),
                "en-US",
                "json",
                "m",
                Common.IBM_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable d = observable.subscribeWith(new DisposableObserver<Forecast12Hours>() {
            @Override
            public void onNext(@NonNull Forecast12Hours forecast12Hours) {
                postsMutableLiveData.setValue(forecast12Hours);
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
