package com.salem.amna.presentation.common.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.datatransport.runtime.logging.Logging
import com.google.android.gms.maps.model.LatLng
import com.salem.amna.R

/**
 * Created by  Šä£êµ ©™ on 19/04/2022
 * ->
 */

class LocationPermission(private val mActivity: Context) {
    private lateinit var mPermission: OnLocationPermissionListener
    private val REQUEST_PICKLOCATION: Int = View.generateViewId()

    fun requestPermissions(permission: OnLocationPermissionListener) {
        mPermission = permission
        ActivityCompat.requestPermissions(
            mActivity as Activity,
            arrayOf("android.permission.ACCESS_FINE_LOCATION"),
            REQUEST_PICKLOCATION
        )
    }

    fun onRequestPermissions(requestCode: Int, grantResults: IntArray, gpsProvider: Boolean) {
        if (requestCode == REQUEST_PICKLOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                if (ContextCompat.checkSelfPermission(
                        mActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if (checkIfGPSEnable(gpsProvider))
                        mPermission.onPermissionSuccess()
                }
            } else {
                mPermission.onPermissionFailed()
            }
        }
    }

    private fun checkIfGPSEnable(gpsProvider: Boolean): Boolean {
        val locationManager =
            mActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        ) {
            if (gpsProvider)
                displayPromptForEnablingGPS()
            else
                mPermission.onPermissionSuccess()
            return false
        }
        return true
    }

    private fun displayPromptForEnablingGPS() {
        val builder = AlertDialog.Builder(mActivity)
        val action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
        builder.setMessage(mActivity.getString(R.string.enable_gps))
            .setPositiveButton(
                mActivity.getString(R.string.ok)
            ) { dialog, _ ->
                dialog.dismiss()
                mActivity.startActivity(Intent(action))
                mPermission.onPermissionFailed()
            }
            .setNegativeButton(
                mActivity.getString(R.string.cancel)
            ) { dialog, _ ->
                dialog.dismiss()
                mPermission.onPermissionFailed()
            }
        builder.create().show()
    }

    @SuppressLint("MissingPermission")
    fun findLocation(location: (latLng: LatLng, permissionSuccess: Boolean) -> Unit) {
        val mPermissionSuccess: Boolean
        val mNetworkSuccess: Boolean
        var mLocationSuccess = false
        var mLatLng: LatLng? = null
        if (ContextCompat.checkSelfPermission(
                mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            val locationContext = Context.LOCATION_SERVICE
            val locationManager = mActivity.getSystemService(locationContext) as LocationManager
            val providers = locationManager.getProviders(true)
            mPermissionSuccess = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            mNetworkSuccess = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            providers.forEach {
                locationManager.requestLocationUpdates(
                    it,
                    1000L,
                    0f,
                    object : LocationListener {
                        override fun onLocationChanged(locat: Location) {
                            if (locat != null && !mLocationSuccess) {
                                mLatLng = LatLng(locat.latitude, locat.longitude)
                                location(mLatLng!!, mPermissionSuccess || mNetworkSuccess)
                                mLocationSuccess = true
                                locationManager.removeUpdates(this)
                            } else if (mLatLng != null) {
                                locationManager.removeUpdates(this)
                            }

                            Log.e("Location Permission", "${locat.latitude}, ${locat.longitude}")

                        }

                        override fun onStatusChanged(
                            provider: String?,
                            status: Int,
                            extras: Bundle?
                        ) {
                        }

                        override fun onProviderEnabled(provider: String) {}

                        override fun onProviderDisabled(provider: String) {}

                    })
//                val loc = locationManager.getLastKnownLocation(it)
//                loc?.let { loca ->
//                    Logging.log("${loca.latitude}, ${loca.longitude}")
//                    if (!mLocationSuccess) {
//                        mLatLng = LatLng(loca.latitude, loca.longitude)
//                        location(mLatLng!!, mPermissionSuccess || mNetworkSuccess)
//                        mLocationSuccess = true
//                    }
//                }
            }
        }
    }
}

interface OnLocationPermissionListener {
    fun onPermissionSuccess()
    fun onPermissionFailed()
}