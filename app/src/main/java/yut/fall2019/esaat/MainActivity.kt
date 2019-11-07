package yut.fall2019.esaat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RawRes
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref: SharedPreferences = getSharedPreferences("ESAaT", 0)
        if (!sharedPref.getBoolean("first_time", false)) {
            //val db = TinyDB(applicationContext)
            //val questions = Gson().fromJson(resources.openRawResource(R.raw.questions).bufferedReader().use { it.readText() }, QuestionModel::class.java)
            val editor = sharedPref.edit()
            editor.putString("questions",resources.openRawResource(R.raw.questions).bufferedReader().use { it.readText()})
            editor.apply()
            editor.putBoolean("first_time", true)
            editor.apply()
        }

        val startButton: Button = findViewById(R.id.start_survey_button)
        val checkButton: Button = findViewById(R.id.check_score_button)
        startButton.setOnClickListener {
            val intent = Intent(applicationContext,QuestionActivity::class.java)
            startActivity(intent)
        }
        checkButton.setOnClickListener {
            val intent = Intent(applicationContext,ReviewActivity::class.java)
            startActivity(intent)
        }

    }
}
