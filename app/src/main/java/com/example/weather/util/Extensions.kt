package com.example.weather.util

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather.R
import com.fondesa.kpermissions.request.PermissionRequest
import java.text.SimpleDateFormat
import java.util.Date

fun RecyclerView.clearReference(lifecycle: Lifecycle) {
    lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            this@clearReference.adapter = null
            super.onDestroy(owner)
        }
    })
}

internal fun Context.showGrantedToast() {
    val msg = getString(R.string.granted_permissions)
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

internal fun Context.showRationaleDialog(
    permissionRequest: PermissionRequest,
    onIsCheckedChange: () -> Unit
) {
    AlertDialog.Builder(this)
        .setCancelable(false)
        .setMessage(R.string.rationale_permissions)
        .setPositiveButton(R.string.request_again) { _, _ ->
            permissionRequest.send()
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

fun Context.showLocationIsDisabledAlert(
    enableLocationProviders: () -> Unit,
    stopAutoLocation: () -> Unit
) {
    AlertDialog.Builder(this)
        .setTitle(R.string.enable_location_title)
        .setMessage(R.string.enable_location_description)
        .setCancelable(false)
        .setPositiveButton(R.string.enable) { _, _ ->
            enableLocationProviders()
        }
        .setNegativeButton(R.string.stop) { _, _ ->
            stopAutoLocation()
        }.show()
}

fun Context.showErrorCoordinatesDialog(
    tryAgain: () -> Unit,
    close: () -> Unit
) {
    AlertDialog.Builder(this)
        .setTitle(R.string.error)
        .setMessage(R.string.error_coordinates)
        .setCancelable(false)
        .setPositiveButton(R.string.try_again) { _, _ -> tryAgain() }
        .setNegativeButton(R.string.close) { _, _ -> close() }
        .show()
}

fun Context.isLocationEnabled(): Boolean {
    val lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
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

fun convertDateToDay(dt: Int): String? {
    val date = Date((dt * 1000L))
    val sdf = SimpleDateFormat("EEEE")
    return sdf.format(date)
}
