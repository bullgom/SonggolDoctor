package studio.sw.mobile.songgoldoctor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_diagnosis.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DiagnosisFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DiagnosisFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DiagnosisFragment : Fragment() {
    companion object {
        @JvmStatic
        private val STATE_DIAGLIST = "diaglist"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var listView: ListView
    private lateinit var rootView: View
    private lateinit var diagList: ArrayList<Diagnosis>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        diagList = ArrayList<Diagnosis>()
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_diagnosis, container, false)
        listView = rootView.findViewById(R.id.diagnosisList)

        test2()
        listView.adapter = DiagnosisAdapter(activity as Activity, diagList)
        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            val intent = Intent(context, DiagnosisActivity::class.java)
            intent.putExtra("diagnosis", diagList[position])
            startActivity(intent)
        }

        return rootView
    }

    private fun test() {
        repeat(10) {
            diagList.add(DummyData.dummyDiagnosis())
        }
    }

    private fun test2()
    {
        diagList.add(DummyData.showDummyDiagnosis1())
        diagList.add(DummyData.showDummyDiagnosis2())
    }

    class DiagnosisAdapter(private var activity: Activity, private var items: ArrayList<Diagnosis>) :
        ArrayAdapter<Diagnosis>(activity, 0, items) {
        private class ViewHolder{
            lateinit var txtDate: TextView
            lateinit var txtHospital: TextView
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: View?
            val viewHolder: ViewHolder
            if (convertView == null) {
                val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                view = inflater.inflate(R.layout.diagnosis_list_item, null)
                viewHolder = ViewHolder()
                viewHolder.txtDate = view.findViewById(R.id.diagnosis_date)
                viewHolder.txtHospital = view.findViewById(R.id.diagnosis_hospital)
                viewHolder.txtDate.text = "${getItem(position).date.getMonth()}/${getItem(position).date.getDate()}"
                viewHolder.txtHospital.text = getItem(position).hospital
            } else {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }

            return view as View
        }

        override fun getItem(i: Int): Diagnosis {
            return items[i]
        }

        override fun getItemId(i: Int): Long {
            return i.toLong()
        }

        override fun getCount(): Int {
            return items.size
        }


    }
}
