package studio.sw.mobile.songgoldoctor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*

class LoginActivity : AppCompatActivity() {

    private lateinit var id: EditText
    private lateinit var pw: EditText
    private lateinit var save: CheckBox
    private lateinit var login: Button
    private lateinit var signin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        id = findViewById(R.id.editID)
        pw = findViewById(R.id.editPW)
        save = findViewById(R.id.checkSave)
        login = findViewById(R.id.buttonLogin)
        signin = findViewById(R.id.buttonSignin)

        login.setOnClickListener{
            intent
        }

        signin.setOnClickListener{

        }
    }
}