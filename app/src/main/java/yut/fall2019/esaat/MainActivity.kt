package yut.fall2019.esaat

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import androidx.core.app.NotificationManagerCompat
import android.app.Activity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref: SharedPreferences = getSharedPreferences("ESAaT", 0)
        if (!sharedPref.getBoolean("first_time", false)) {
            NotificationHelper.createNotificationChannel(this, NotificationManagerCompat.IMPORTANCE_DEFAULT, false, getString(R.string.app_name), "App notification channel.")

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

        val intent = Intent(this, NotificationHelper.notify(this)::class.java)
        val manager = getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(this,
                0, intent, 0)
        val cal = Calendar.getInstance()
        manager.setRepeating(AlarmManager.RTC_WAKEUP, cal.timeInMillis, (24 * 60 * 60 * 1000).toLong(), pendingIntent)

    }
}
