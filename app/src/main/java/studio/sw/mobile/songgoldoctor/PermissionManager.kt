package studio.sw.mobile.songgoldoctor

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

class PermissionManager(val activity: Activity){
    companion object {
        @JvmStatic private val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1
        @JvmStatic private val PERMISSION_REQUEST_ACCESS_COARSE_LOCATION = 2
        @JvmStatic private val PERMISSION_REQUEST_CALL_PHONE = 3
        @JvmStatic fun checkLocationPermission (activity: Activity){
            if (ActivityCompat.checkSelfPermission(activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(activity,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_ACCESS_FINE_LOCATION)
            if(ActivityCompat.checkSelfPermission(activity,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(activity,
                    arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_ACCESS_COARSE_LOCATION)
        }
        @JvmStatic fun checkCallPermission(activity: Activity){
            if (ActivityCompat.checkSelfPermission(activity,
                    android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(activity,
                    arrayOf(android.Manifest.permission.CALL_PHONE), PERMISSION_REQUEST_CALL_PHONE)
        }
    }

    fun setup(){
        checkLocationPermission(activity)
        checkCallPermission(activity)
    }



}