package studio.sw.mobile.songgoldoctor

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import java.lang.Exception
import android.widget.GridView

class HospitalActivity : Activity() {
    private var mMap: GoogleMap? = null
    private lateinit var hospital: Hospital
    private lateinit var mMapView: MapView
    private lateinit var mCurrentLatLng: LatLng
    private lateinit var mHospitalNameView: TextView
    private lateinit var mHospitalAddressView: TextView
    private lateinit var mGridLayout: GridView
    private lateinit var mGridLayoutAdapter: HospitalActivityTableAdapter
    private val mDefaultLocation = LatLng(37.597470317773286, 126.86515811830759)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)
        PermissionManager.checkLocationPermission(this)
        mMapView = findViewById(R.id.hospital_activity_mapview)
        mMapView.onCreate(savedInstanceState)
        try {
            MapsInitializer.initialize(applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mMapView.getMapAsync(OnMapReadyCallBackHandler())
        hospital = intent.getParcelableExtra(HOSPITAL_OBJECT)

        mHospitalNameView = findViewById(R.id.hospital_activity_hospital_name)
        mHospitalNameView.text = hospital.name
        mHospitalAddressView = findViewById(R.id.hospital_activity_hospital_address)
        mHospitalAddressView.text = hospital.address
        mGridLayout = findViewById(R.id.hospital_activity_tablelayout)
        mGridLayoutAdapter = HospitalActivityTableAdapter(this, hospital.workDays)
        mGridLayout.adapter = mGridLayoutAdapter
        setGridViewHeightBasedOnChildren(mGridLayout,2)
    }
    private fun setGridViewHeightBasedOnChildren(gridView: GridView, columns: Int) {
        val listAdapter = gridView.adapter
            ?: // pre-condition
            return

        var totalHeight = 0
        val items = listAdapter.count
        var rows = 0

        val listItem = listAdapter.getView(0, null, gridView)
        listItem.measure(0, 0)
        totalHeight = listItem.measuredHeight

        var x :Float
        if (items > columns) {
            x = (items / columns).toFloat()
            rows = (x + 1).toInt()
            totalHeight *= rows
        }

        val params = gridView.layoutParams
        params.height = totalHeight
        gridView.layoutParams = params

    }

    inner class OnMapReadyCallBackHandler : OnMapReadyCallback {
        override fun onMapReady(map: GoogleMap?) {
            mCurrentLatLng = mDefaultLocation
            mMap = map
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(hospital.position, 16f))
            mMapView.onResume()
        }
    }
}

class HospitalActivityTableAdapter(
    context: Activity,
    private val workdays: ArrayList<WorkDay>
) :
    ArrayAdapter<WorkDay>(context, 0, workdays) {
    private class ViewHolder {
        lateinit var seperator: View
        lateinit var weekDays: TextView
        lateinit var workTime: TextView
    }

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return workdays.size
    }

    override fun getItem(position: Int): WorkDay {
        return workdays[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val viewHolder: ViewHolder
        val view: View
        if (parent == null) return null

        if (convertView == null) {
            view = inflater.inflate(R.layout.workday_item, null)
            viewHolder = ViewHolder()
            viewHolder.seperator = view.findViewById(R.id.workday_item_lineseperator)
            if(workdays[position].dayOfWeek in arrayOf(Week.Saturday, Week.Sunday, Week.Holiday))
                viewHolder.seperator.setBackgroundColor(parent.resources.getColor(R.color.colorRed))
            else viewHolder.seperator.setBackgroundColor(parent.resources.getColor(R.color.colorBlue))
            viewHolder.weekDays = view.findViewById(R.id.workday_item_day)
            viewHolder.weekDays.text = getItem(position).dayOfWeek.toString()
            viewHolder.workTime = view.findViewById(R.id.workday_item_time)
            viewHolder.workTime.text = workTimesToString(getItem(position).workTimes)
        } else view = convertView

        return view
    }

    private fun workTimesToString(workTImes:ArrayList<WorkTime>):String{
        var result:String = ""
        val iterator = workTImes.listIterator()
        while(iterator.hasNext()){
            result += iterator.next()
            if(iterator.hasNext()) result += ", "
        }
        return result
    }
}

