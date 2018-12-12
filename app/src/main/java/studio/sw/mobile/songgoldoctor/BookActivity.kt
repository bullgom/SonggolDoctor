package studio.sw.mobile.songgoldoctor

import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*
import android.view.View.MeasureSpec


class BookActivity : Activity() {
    private lateinit var hospital: Hospital
    private lateinit var bookButton: Button
    private lateinit var dateCheckBox: CheckBox
    private lateinit var datePicker: DatePicker
    private lateinit var timeCheckBox: CheckBox
    private lateinit var listView: ListView
    private lateinit var listAdapter: ListViewAdapter
    private lateinit var timeList: ArrayList<ArrayList<Time>>
    private var selectedTime: Time? = null
    private var timeCorrect: Boolean = false
    private var dateCorrect: Boolean = false
    private var lastDate = Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH)
    private val calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        hospital = intent.getParcelableExtra(HOSPITAL_OBJECT)
        dateCheckBox = findViewById(R.id.book_activity_pick_date_check_box)
        timeCheckBox = findViewById(R.id.book_activity_pick_time_check_box)
        timeList = workDaysToList(hospital.workDays)
        listAdapter = ListViewAdapter(this, timeList[calendar.get(Calendar.DAY_OF_WEEK)])
        listView = findViewById(R.id.book_activity_pick_time_list_view)
        listView.adapter = listAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            selectedTime = listView.adapter.getItem(position) as Time?
            timeCheckBox.isChecked = true
            timeCorrect = true
            updateButton()
        }
        setListViewHeightBasedOnChildren(listView)
        listAdapter.notifyDataSetChanged()
        datePicker = findViewById(R.id.book_activity_calendar)
        datePicker.minDate = System.currentTimeMillis() - 1000
        datePicker.updateDate(2018, 11, 12) // Stupid
        datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            val newDate = Date(year, monthOfYear, dayOfMonth - 1)
            calendar.time = newDate
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) //Sunday is 1 Saturday is 7
            var isChanged = false
            for (day in hospital.workDays) {
                if (day.dayOfWeek.ordinal == dayOfWeek - 1) {
                    lastDate = newDate
                    listAdapter.clear()
                    listAdapter.addAll(workDaysToList(hospital.workDays)[day.dayOfWeek.ordinal - 1])
                    isChanged = true
                    dateCheckBox.isChecked = true
                    dateCorrect = true
                    break
                }
            }
            if (!isChanged) {
                Toast.makeText(applicationContext, "영업하지 않는 날짜입니다.", Toast.LENGTH_LONG).show()
                listAdapter.notifyDataSetChanged()
                dateCheckBox.isChecked = false
                dateCorrect = false
            }
            listAdapter.notifyDataSetChanged()

            updateButton()
        }
        bookButton = findViewById(R.id.book_activity_apply_button)
        bookButton.isEnabled = false
        bookButton.setOnClickListener {
            Toast.makeText(this, "Request sent", Toast.LENGTH_LONG).show()
            finish()
        }
        //TODO( Send request to server )
    }

    private fun updateButton() {
        bookButton.isEnabled = timeCorrect && dateCorrect
    }

    private fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter ?: return

        val desiredWidth = MeasureSpec.makeMeasureSpec(listView.width, MeasureSpec.UNSPECIFIED)

        var totalHeight = 0
        val items = listAdapter.count

        val listItem = listAdapter.getView(0, null, listView)
        listItem.measure(0, 0)
        totalHeight = listItem.measuredHeight

        totalHeight *= items

        val params = listView.layoutParams
        params.height = totalHeight
        listView.layoutParams = params
    }
}

class ListViewAdapter(context: Context, var workTimes: ArrayList<Time>) :
    ArrayAdapter<Time>(context, 0, workTimes) {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private class ViewHolder {
        lateinit var time: TextView
    }

    override fun getCount(): Int {
        return workTimes.size
    }

    override fun getItem(position: Int): Time? {
        return workTimes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val viewHolder: ViewHolder?
        val view: View

        if (convertView == null) {
            view = inflater.inflate(R.layout.activity_book_item, null)
            viewHolder = ViewHolder()
            viewHolder.time = view.findViewById(R.id.book_activity_item_text)
            viewHolder.time.text = getItem(position).toString()
        } else
            view = convertView

        return view
    }
}

fun getDividedList(time: WorkTime): ArrayList<Time> {
    val result = ArrayList<Time>()
    var segment = time.start

    while (segment <= time.end) {
        result.add(segment)
        segment += 30
    }

    return result
}

fun workDaysToList(days: ArrayList<WorkDay>): ArrayList<ArrayList<Time>> {
    val result = ArrayList<ArrayList<Time>>()
    for (day in days)
        result.add(getDividedList(day.workTime))

    return result
}

