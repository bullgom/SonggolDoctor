package studio.sw.mobile.songgoldoctor

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.ListAdapter
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class BookFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var listView: ListView
    private lateinit var adapter: ListAdapter
    private var list = ArrayList<BookRecord>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_book, container, false)
        test(10)
        //TODO(Retrieve BookRecord from server)
        adapter = BookListAdapter(activity as Activity, list)
        listView = rootView.findViewById(R.id.book_fragment_listView)
        listView.adapter = adapter
        return rootView
    }

    fun test(count: Int) {
        repeat(count) {
            list.add(DummyData.dummyBookRecored())
        }
    }
}

class BookListAdapter(
    context: Activity,
    private val source: ArrayList<BookRecord>
) : ArrayAdapter<BookRecord>(context, 0, source) {
    private class ViewHolder {
        lateinit var name: TextView
        lateinit var date: TextView
        lateinit var status: TextView
        lateinit var reason: TextView
    }

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return source.size
    }

    override fun getItem(postion: Int): BookRecord {
        return source[postion]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder
        val view: View

        if (convertView == null) {
            view = inflater.inflate(R.layout.fragment_book_item, null)
            viewHolder = ViewHolder()
            val item = getItem(position)
            viewHolder.name = view.findViewById(R.id.book_fragment_hospital_name)
            viewHolder.name.text = item.hospital.name
            viewHolder.date = view.findViewById(R.id.book_fragment_date)
            val simpleDate: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd");
            val date = simpleDate.format(item.date)
            viewHolder.date.text = date
            viewHolder.reason = view.findViewById(R.id.book_fragment_reason)
            viewHolder.status = view.findViewById(R.id.book_fragment_request_status)
            when (getItem(position).status) {
                Status.None -> {
                    viewHolder.status.text = context.getString(R.string.book_status_none)
                    viewHolder.reason.visibility = View.INVISIBLE
                }
                Status.Declined -> {
                    viewHolder.status.text = context.getString(R.string.book_status_declined)
                    viewHolder.reason.visibility = View.VISIBLE
                }
                Status.Accepted -> {
                    viewHolder.status.text = context.getString(R.string.book_status_accepted)
                    viewHolder.reason.visibility = View.INVISIBLE
                }
            }
        } else
            view = convertView
        return view
    }

}