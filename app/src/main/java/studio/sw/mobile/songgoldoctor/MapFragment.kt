package studio.sw.mobile.songgoldoctor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.PlaceDetectionClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import java.lang.Exception

class MapFragment : Fragment() {
    private var mMap: GoogleMap? = null
    private lateinit var mMapView: MapView
    private lateinit var mGeoDataClient: GeoDataClient
    private lateinit var mPlaceDetectionClient: PlaceDetectionClient
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mLocationManager: LocationManager
    private lateinit var mToCurrentLocationButton: FloatingActionButton
    private var mHospitalList: ArrayList<Hospital> = ArrayList<Hospital>()
    private var mCurrentBestLocation: Location? = null
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
        mMap?.setOnMapClickListener {
            mMap?.moveCamera(CameraUpdateFactory.newLatLng(it))
            mHospitalList.clear()
            test(10, it, 0.0005)
            drawCircles()
        }
        try {
            MapsInitializer.initialize(activity?.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mMapView.getMapAsync(OnMapReadyCallBackHandler())

        mToCurrentLocationButton = rootView.findViewById(R.id.map_fragment_current_location)
        mToCurrentLocationButton.setOnClickListener {
            mCurrentBestLocation = getLastBestLocation()
            mCurrentLatLng = locationToLatLng(mCurrentBestLocation)
            mHospitalList.clear()
            mMap?.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLatLng))
            test(10, mCurrentLatLng, 0.0005)
            drawCircles()
        }
        //Add dummy data
        //TODO( Get data from server )

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

    fun drawCircles() {
        mMap?.clear()
        for (hospital in mHospitalList) {
            mMap?.addCircle(
                CircleOptions()
                    .center(hospital.position)
                    .radius(5.0)
                    .fillColor(Color.WHITE)
                    .strokeColor(Color.BLUE)
            )
        }
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
        val locationTimeGPS: Long = locationGPS?.time ?: 0
        val locationTimeNet: Long = locationNet?.time ?: 0

        return if (0 < locationTimeGPS - locationTimeNet) locationGPS
        else locationNet
    }

    private fun locationToLatLng(location: Location?): LatLng? {
        return if (location == null) null
        else LatLng(location.latitude, location.longitude)
    }


    private fun test(count: Int, center: LatLng?, radius: Double) {
        repeat(count) {
            mHospitalList.add(DummyData.dummyHospitalWithRange(center, radius))
        }
    }


    inner class OnMapReadyCallBackHandler : OnMapReadyCallback {
        override fun onMapReady(map: GoogleMap?) {
            mCurrentLatLng = locationToLatLng(getLastBestLocation())
            if (mCurrentLatLng == null) mCurrentLatLng = mDefaultLocation
            mMap = map
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLatLng, 16f))
            test(10, mCurrentLatLng, 0.0005)
            drawCircles()
        }
    }
}
