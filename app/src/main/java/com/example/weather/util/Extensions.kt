package com.example.weather.util

import android.Manifest
import android.app.ProgressDialog.show
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.example.weather.ListLocationsEvent
import com.example.weather.R
import com.fondesa.kpermissions.PermissionStatus
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.builder.PermissionRequestBuilder
import com.fondesa.kpermissions.isGranted
import com.fondesa.kpermissions.request.PermissionRequest
import dagger.hilt.android.qualifiers.ApplicationContext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*



internal fun Context.showGrantedToast() {
    val msg = getString(R.string.granted_permissions)
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

internal fun Context.showRationaleDialog(
    request: PermissionRequest,
    onIsCheckedChange: () -> Unit
) {

    AlertDialog.Builder(this)
        .setCancelable(false)
        .setMessage(R.string.rationale_permissions)
        .setPositiveButton(R.string.request_again) { _, _ ->
            request.send()
        }
        .setNegativeButton(android.R.string.cancel) { _, _ -> onIsCheckedChange() }
        .show()
}

internal fun Context.showPermanentlyDeniedDialog() {


    AlertDialog.Builder(this)
        .setCancelable(false)
        .setMessage(R.string.rationale_permissions)
        .setPositiveButton(R.string.action_settings) { _, _ ->
            val intent = createAppSettingsIntent()
            startActivity(intent)
        }
        .setNegativeButton(android.R.string.cancel, null)
        .show()
}

private fun Context.createAppSettingsIntent() = Intent().apply {
    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    data = Uri.fromParts("package", packageName, null)
}

private inline fun <reified T : PermissionStatus> List<PermissionStatus>.toMessage(): String = filterIsInstance<T>()
    .joinToString { it.permission }

fun Context.showLocationIsDisabledAlert(
    enableLocationProviders: () -> Unit,
    stopAutoLocation: () -> Unit
) {
   AlertDialog.Builder(this)
       .setTitle("Enable Location Providers")
       .setMessage("The auto-location feature relies on at least one location provider")
       .setCancelable(false)
       .setPositiveButton("ENABLE PROVIDERS") { _, _ ->
            enableLocationProviders()
       }
       .setNegativeButton("STOP AUTO-LOCATION") { _, _ ->
            stopAutoLocation()
       }.show()
}

fun Context.showErrorCoordinatesDialog(
    tryAgain: () -> Unit,
    close: () -> Unit
) {
    AlertDialog.Builder(this)
        .setTitle("Error")
        .setMessage("Make sure you write the location correctly and internet connexion is not missing")
        .setCancelable(false)
        .setPositiveButton("TRY AGAIN") { _, _ -> tryAgain() }
        .setNegativeButton("CLOSE") { _, _ -> close() }
        .show()
}

fun isLocationEnabled(mContext: Context): Boolean {
    val lm = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER)
}

fun bindImage(imgView: ImageView, imgUrl: String?) {
    Glide.with(imgView.context)
        .load(imgUrl)
        .into(imgView)
}


fun convertDateToHour(dt: Int): String? {
    val date = Date((dt * 1000L))
    val sdf = SimpleDateFormat("HH:mm")
    return sdf.format(date)
}

fun convertToDay(dt: Int): String? {
    val date = Date((dt * 1000L))
    val sdf = SimpleDateFormat("EEEE")
    return sdf.format(date)
}


