package yut.fall2019.esaat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.google.gson.Gson


class QuestionActivity : AppCompatActivity(){   //, QuestionFragment.OnFragmentInteractionListener

    private val arrayListFragments:ArrayList<Fragment>  = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        //val db = TinyDB(applicationContext)

        val pager:ViewPager = findViewById(R.id.pager)
        val sharedPref: SharedPreferences = getSharedPreferences("ESAaT", 0)

        val questionString = sharedPref.getString("questions","")
        val questions = Gson().fromJson(questionString,QuestionModel::class.java)
        val t = questions.questions[0].Title
        Log.d("TAG", t)
        //TODO fill the arrayList with Fragments
        val pagerAdapter = QuestionFragmentAdapter(supportFragmentManager,arrayListFragments)
        pager.adapter =pagerAdapter
    }

    fun goToNext() {
        val pager:ViewPager = findViewById(R.id.pager)
        pager.currentItem = pager.currentItem + 1
    }

    fun surveyCompleted(score:Int){
        val returnIntent = Intent()
        returnIntent.putExtra("score", score)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun onBackPressed() {
        val pager:ViewPager = findViewById(R.id.pager)
        if (pager.currentItem == 0) {
            super.onBackPressed()
        } else {
            pager.currentItem = pager.currentItem - 1
        }
    }
}
