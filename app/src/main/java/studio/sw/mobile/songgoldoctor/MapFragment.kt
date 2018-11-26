package studio.sw.mobile.songgoldoctor

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.PlaceDetectionClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import java.lang.Exception

class MapFragment : Fragment() {
    private var mMap: GoogleMap? = null
    private lateinit var mMapView: MapView
    private lateinit var mGeoDataClient: GeoDataClient
    private lateinit var mPlaceDetectionClient: PlaceDetectionClient
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mLocationManager: LocationManager
    private lateinit var mPlacePicker: PlacePicker
    private var mCurrentBestLocation: Location? = null
        set(value) {
            mCurrentLatLng = locationToLatLng(value)
        }
    private var mCurrentLatLng: LatLng? = null
    private lateinit var toFavoriteButton: ImageButton

    private val mDefaultLocation = LatLng(37.597470317773286, 126.86515811830759)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        PermissionManager.checkLocationPermission(context as Activity)
        val rootView: View = inflater.inflate(R.layout.fragment_map, container, false)
        mMapView = rootView.findViewById(R.id.mapView) as MapView
        mMapView.onCreate(savedInstanceState)
        mMap?.isMyLocationEnabled = true
        mMap?.uiSettings?.isMyLocationButtonEnabled = true

        try {
            MapsInitializer.initialize(activity?.applicationContext)
        } catch (e: Exception) {e.printStackTrace()}

        mMapView.getMapAsync(OnMapReadyCallBackHandler())
        //GeoDataClient provides access to Google's database of local place and business info
        mGeoDataClient = Places.getGeoDataClient(activity as Activity)
        //PlaceDetectionClient provides quick access to the device's current location
        mPlaceDetectionClient = Places.getPlaceDetectionClient(activity as Activity)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity as Activity)
        mLocationManager = (activity as Activity).getSystemService(Context.LOCATION_SERVICE) as LocationManager

        toFavoriteButton = rootView.findViewById(R.id.toFavoriteButton)
        toFavoriteButton.setOnClickListener {
            val intent = Intent(activity, FavoriteListActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }

    fun getLastBestLocation(): Location? {
        if (
            ContextCompat.checkSelfPermission(
                activity as Activity,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                activity as Activity,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }

        val locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        val GPSLocationTime: Long = locationGPS?.time ?: 0
        val NetLocationTime: Long = locationNet?.time ?: 0

        if (0 < GPSLocationTime - NetLocationTime) return locationGPS
        else return locationNet
    }

    fun locationToLatLng(location: Location?): LatLng? {
        if (location?.latitude == null || location?.longitude == null) return null
        else return LatLng(location.latitude, location.longitude)
    }

    inner class OnMapReadyCallBackHandler : OnMapReadyCallback {
        override fun onMapReady(map: GoogleMap?) {
            mCurrentLatLng = mDefaultLocation
            mMap = map
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLatLng, 16f))
        }
    }
}
