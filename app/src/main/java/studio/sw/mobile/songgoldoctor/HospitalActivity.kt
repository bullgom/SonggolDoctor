package studio.sw.mobile.songgoldoctor

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TableLayout
import android.widget.TextView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import java.lang.Exception

class HospitalActivity : Activity() {
    inner class WorkDayHolder {
        var days: ArrayList<Week> = ArrayList<Week>()
        var workTimes: ArrayList<WorkTime> = ArrayList<WorkTime>()
    }

    private var mMap: GoogleMap? = null
    private var workDayList = ArrayList<WorkDayHolder>()
    private lateinit var hospital: Hospital
    private lateinit var mMapView: MapView
    private lateinit var mCurrentLatLng: LatLng
    private lateinit var mHospitalNameView: TextView
    private lateinit var mHospitalAddressView: TextView
    private lateinit var mHospitalWorkTimeTableLayout: TableLayout
    private lateinit var mTableAdapter: ArrayAdapter<WorkDayHolder>
    private val mDefaultLocation = LatLng(37.597470317773286, 126.86515811830759)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)
        PermissionManager.checkLocationPermission(this)
        mMapView = findViewById(R.id.hospital_activity_mapview)
        mMapView.onCreate(savedInstanceState)
        try {
            MapsInitializer.initialize(this.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mMapView.getMapAsync(OnMapReadyCallBackHandler())
        hospital = intent.getParcelableExtra(HOSPITAL_OBJECT)
        for (from in hospital.workDays) {
            for (to in workDayList) {
                if (from.workTimes == to.workTimes)
                    to.days.add(from.dayOfWeek)
                else {
                    val newEntry = WorkDayHolder()
                    newEntry.days.add(from.dayOfWeek)
                    newEntry.workTimes = from.workTimes
                }
            }
        }
        mHospitalNameView = findViewById(R.id.hospital_activity_hospital_name)
        mHospitalNameView.text = hospital.name
        mHospitalAddressView = findViewById(R.id.hospital_activity_hospital_address)
        mHospitalAddressView.text = hospital.address
        mHospitalWorkTimeTableLayout = findViewById(R.id.hospital_activity_tablelayout)
        mTableAdapter = HospitalActivityTableAdapter(this, workDayList)
        for (i: Int in 0..mTableAdapter.count) {
            mHospitalWorkTimeTableLayout.addView(mTableAdapter.getView(i, null, mHospitalWorkTimeTableLayout))
        }
    }

    inner class OnMapReadyCallBackHandler : OnMapReadyCallback {
        override fun onMapReady(map: GoogleMap?) {
            mCurrentLatLng = mDefaultLocation
            mMap = map
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLatLng, 16f))
        }
    }

    class HospitalActivityTableAdapter(
        context: Activity,
        val workdays: ArrayList<HospitalActivity.WorkDayHolder>
    ) :
        ArrayAdapter<HospitalActivity.WorkDayHolder>(context, 0, workdays) {
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

        override fun getItem(position: Int): WorkDayHolder {
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
                //TODO(여기 에러난거 고치기
                // TODO(Caused by: android.view.InflateException: Binary XML file line #10: Attempt to invoke virtual method 'boolean java.lang.String.equals(java.lang.Object)' on a null object reference)
                view = inflater.inflate(R.layout.workday_item, null)
                viewHolder = ViewHolder()
                viewHolder.seperator = view.findViewById(R.id.workday_item_lineseperator)
                var workTime = Time(0, 0)
                for (i in workdays[position].workTimes) {
                    workTime += i.end - i.start
                    if (workTime > Time(5, 0))
                        viewHolder.seperator.setBackgroundColor(parent.resources.getColor(R.color.colorBlue))
                    else viewHolder.seperator.setBackgroundColor(parent.resources.getColor(R.color.colorRed))
                }
                viewHolder.weekDays = view.findViewById(R.id.workday_item_day)
                viewHolder.weekDays.text = getItem(position).days.toString()
                viewHolder.workTime = view.findViewById(R.id.workday_item_time)
                viewHolder.workTime.text = getItem(position).workTimes.toString()
            } else {
                viewHolder = convertView.tag as ViewHolder
                view = convertView
            }
            return view
        }


    }
}

