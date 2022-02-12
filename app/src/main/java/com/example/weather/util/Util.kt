package com.example.weather.util

import android.content.Context
import android.location.LocationManager
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

fun isLocationEnabled(mContext: Context): Boolean {
    val lm = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER)
}

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}

fun bindImage(imgView: ImageView, imgUrl: String?) {
    Glide.with(imgView.context)
        .load(imgUrl)
        .into(imgView)
}


fun convertToHour(dt: Int): String? {
    val date = Date((dt * 1000L))
    val sdf = SimpleDateFormat("HH:mm")
    return sdf.format(date)
}

fun convertToDay(dt: Int): String? {
    val date = Date((dt * 1000L))
    val sdf = SimpleDateFormat("EEEE")
    return sdf.format(date)
}