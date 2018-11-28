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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class RecentFragment : Fragment() {
    companion object {
        @JvmStatic
        private val STATE_RECENTLIST = "recentlist"
    }

    private lateinit var listView: ListView
    private lateinit var rootView: View
    private lateinit var recentList: ArrayList<Hospital>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState != null) {
            @Suppress("UNCHECKED_CAST")
            recentList =
                    savedInstanceState.getParcelable<BaseParcelable>(RecentFragment.STATE_RECENTLIST).value as ArrayList<Hospital>
        }else recentList = ArrayList<Hospital>()
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_recentlist, container, false)
        listView = rootView.findViewById(R.id.recentList)

        listView.adapter = RecentListAdapter(activity as Activity, recentList)
        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            val intent = Intent(context, HospitalActivity::class.java)
            startActivity(intent)
        }
        test()
        return rootView
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(STATE_RECENTLIST, BaseParcelable(recentList))
        super.onSaveInstanceState(outState)
    }

    private fun test() {
        repeat(10){
            recentList.add(DummyData.dummyHospital())
        }
    }
}

class RecentListAdapter(
    context: Activity,
    private val source: ArrayList<Hospital>
) : ArrayAdapter<Hospital>(context, 0, source) {
    private class ViewHolder {
        lateinit var name: TextView
        lateinit var book: TextView
        lateinit var phone: TextView
    }

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return source.size
    }

    override fun getItem(position: Int): Hospital {
        return source[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val viewHolder: RecentListAdapter.ViewHolder
        val view: View

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.favorite_list_item, null)
            viewHolder = RecentListAdapter.ViewHolder()
            viewHolder.name = view.findViewById(R.id.hospital_name)
            viewHolder.name.text = (getItem(position) as Hospital).name
            viewHolder.book = view.findViewById(R.id.hostpital_book)
            viewHolder.book.setOnClickListener {
                val intent = Intent(context, HospitalActivity::class.java)
                context.startActivity(intent)
            }
            viewHolder.phone = view.findViewById(R.id.hospital_call)
            viewHolder.phone.setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:" + getItem(position).phoneNumber)
                PermissionManager.checkCallPermission(context as Activity)
                context.startActivity(intent)
            }
        } else {
            viewHolder = convertView.tag as RecentListAdapter.ViewHolder
            view = convertView
        }
        return view
    }
}
