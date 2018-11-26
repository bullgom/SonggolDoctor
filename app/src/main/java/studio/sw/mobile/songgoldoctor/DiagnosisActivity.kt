package studio.sw.mobile.songgoldoctor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView
import java.util.*

class DiagnosisActivity : AppCompatActivity() {
    private lateinit var comment: TextView
    private lateinit var date: TextView
    private lateinit var drug: TextView
    private lateinit var hospital: TextView
    private lateinit var howMany: TextView
    private lateinit var backButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnosis)

        comment = findViewById(R.id.diag_act_comment)
        date = findViewById(R.id.diag_act_date)
        drug = findViewById(R.id.diag_act_drug)
        hospital = findViewById(R.id.diag_act_hospital)
        howMany = findViewById(R.id.diag_act_howMany)
        backButton = findViewById(R.id.diag_act_backButton)


        backButton.setOnClickListener {
            finish()
        }

        if (intent.hasExtra("diagnosis")) {
            var intent = intent.getParcelableExtra<Diagnosis>("diagnosis")

            comment.text = intent.futureMedicalOpinion
            date.text = "${intent.date.getMonth()}/${intent.date.getDate()}"
            drug.text = intent.medicine
            hospital.text = intent.hospital
            howMany.text = intent.howMany.toString()


        }
    }
}
