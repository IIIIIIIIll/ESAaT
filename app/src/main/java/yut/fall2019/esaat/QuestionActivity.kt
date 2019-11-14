package yut.fall2019.esaat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import com.google.gson.Gson


class QuestionActivity : AppCompatActivity(),QuestionFragment.OnFragmentInteractionListener,EndFragment.OnFragmentInteractionListener{
    override fun onFragmentInteraction(uri: Uri) {

    }   //, QuestionFragment.OnFragmentInteractionListener

    private val fragmentsList:ArrayList<Fragment>  = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        //val db = TinyDB(applicationContext)

        val pager:ViewPager = findViewById(R.id.pager)
        val sharedPref: SharedPreferences = getSharedPreferences("ESAaT", 0)

        val questionString = sharedPref.getString("questions","")
        //Log.d("TAG",questionString + " data")
        val questions = Gson().fromJson(questionString,QuestionModel::class.java)
        val questionList = questions.questions
        //Log.d("TAG",questionList[0].title + " title")
        for (question: QuestionModel.Question in questionList) {
            val bundle= Bundle()
            bundle.putSerializable("question",question)
            val frag = QuestionFragment.newInstance(question)
            fragmentsList.add(frag)
        }
        val endFrag = EndFragment.newInstance()
        fragmentsList.add(endFrag)
        //TODO add a final result page
        val pagerAdapter = QuestionFragmentAdapter(supportFragmentManager,fragmentsList)
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
