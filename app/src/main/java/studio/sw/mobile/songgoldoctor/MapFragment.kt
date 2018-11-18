package studio.sw.mobile.songgoldoctor

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HospitalFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HospitalFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MapFragment : Fragment() {
    private var map:GoogleMap? = null
    private lateinit var mapView: MapView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_map, container,false)
        mapView = rootView.findViewById(R.id.mapView) as MapView
        mapView.onCreate(savedInstanceState)

        try{
            MapsInitializer.initialize(activity?.applicationContext)
        }catch (e:Exception){
            e.printStackTrace()
        }

        mapView.getMapAsync(OnMapReadyCallBackHandler(map))
        return rootView
    }

    override fun onResume(){
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}

class OnMapReadyCallBackHandler(var googleMap: GoogleMap?):OnMapReadyCallback{
    override fun onMapReady(map: GoogleMap?) {
        googleMap = map

        val sydny = LatLng(-34.0, 151.0)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(sydny,16f))
    }
}
