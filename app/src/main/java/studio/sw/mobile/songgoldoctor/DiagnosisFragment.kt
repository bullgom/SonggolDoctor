package studio.sw.mobile.songgoldoctor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.text.SimpleDateFormat

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
        listView = rootView.findViewById(R.id.diag_fragment_listView)

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
            lateinit var name: TextView
            lateinit var date: TextView
            lateinit var drug: TextView
            lateinit var howMany: TextView
            lateinit var etc: TextView
        }

        private val inflater:LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: View?
            val viewHolder: ViewHolder
            if (convertView == null) {
                view = inflater.inflate(R.layout.fragment_diagnosis_item, null)
                viewHolder = ViewHolder()
                val item = getItem(position)
                viewHolder.name = view.findViewById(R.id.diag_fragment_hospital_name)
                viewHolder.name.text = item.hospital
                viewHolder.date = view.findViewById(R.id.diag_fragment_date)
                val simpleDate = SimpleDateFormat("yyyy/MM/dd");
                val date = simpleDate.format(item.date)
                viewHolder.date.text = date
                viewHolder.drug = view.findViewById(R.id.diag_fragment_drug)
                viewHolder.drug.text = item.medicine
                viewHolder.howMany = view.findViewById(R.id.diag_fragment_howMany)
                viewHolder.howMany.text = item.howMany.toString()
                viewHolder.etc = view.findViewById(R.id.diag_fragment_etc)
                if (item.medicine.length > 1)
                    viewHolder.etc.visibility = View.VISIBLE
            } else {
                view = convertView
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
