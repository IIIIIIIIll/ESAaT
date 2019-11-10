package yut.fall2019.esaat

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ResultViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_view)

        val sharedPref: SharedPreferences = getSharedPreferences("ESAaT", 0)
        val aScore = sharedPref.getInt("aScore",0)
        val dScore = sharedPref.getInt("dScore",0)
        Log.d("TAG", "aScore $aScore")
        Log.d("TAG","dScore $dScore")
    }
}
