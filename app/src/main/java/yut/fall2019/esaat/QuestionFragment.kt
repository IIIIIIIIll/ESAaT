package yut.fall2019.esaat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import android.widget.CompoundButton
import android.util.TypedValue
import android.text.Html
import android.widget.CheckBox
import android.widget.TextView


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "questionData"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [QuestionFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    //private var questionData: String? = null
    private var question: QuestionModel.Question? = null
    private var listener: OnFragmentInteractionListener? = null
    private var checkBoxLayout: LinearLayout? = null
    private var context:FragmentActivity? =null
    private var checkboxes = arrayListOf<CheckBox>()
    private var title: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            question = it.getSerializable(ARG_PARAM1) as QuestionModel.Question?
            //Log.d("TAG", question.toString()+" frag onCreate")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_question, container, false)
        checkBoxLayout= v.findViewById(R.id.linearLayout_checkboxes)
        title=v.findViewById(R.id.title)
        //Log.d("TAG", question.toString()+" frag onCreateView")
        return v
    }

    private fun getScore(s : String): Int {
        //Not at all = 0, Several days = 1, More than half the days = 2, Nearly every day = 3.

        return when(s){
            "Not at all" ->  0
            "Several days" ->  1
            "More than half the days" ->  2
            "Nearly every day" ->  3
            else -> -1
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        context = activity

        title!!.text = question!!.title

        val choices = listOf(question!!.choice1,question!!.choice2,question!!.choice3,question!!.choice4)
        for (choice in choices){
            val cb = CheckBox(context)
            cb.text = choice
            cb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            cb.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            checkBoxLayout!!.addView(cb)
            checkboxes.add(cb)
            cb.setOnCheckedChangeListener { _, _ ->
                run {
                    //Log.d("TAG",cb.text.toString())
                    val sharedPref: SharedPreferences =  context!!.getSharedPreferences("ESAaT", 0)
                    val editor = sharedPref.edit()
                    val scoreName: String = if (question!!.id!! >8){
                        "aScore"
                    }else{
                        "dScore"
                    }
                    val oldScore = sharedPref.getInt(scoreName,0)
                    val currentScore:Int = getScore(cb.text.toString())
                    //Log.d("TAG", currentScore.toString())
                    editor.putInt(scoreName,oldScore+currentScore)
                    editor.apply()
                    if (question!!.id!! == 15){
                        val i = Intent(context,ResultViewActivity::class.java)
                        startActivity(i)
                    }else{
                        (context as QuestionActivity).goToNext()
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            //Log.d("TAG", question?.title)
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param questionData the string of the question
         * @return A new instance of fragment QuestionFragment.
         */
        @JvmStatic
        fun newInstance(questionData: QuestionModel.Question) =
                QuestionFragment().apply {
                    arguments = Bundle().apply {
                        //Log.d("TAG", questionData.title)
//                        Log.d("TAG",questionData.choice1)
//                        Log.d("TAG",questionData.choice2)
//                        Log.d("TAG",questionData.choice3)
//                        Log.d("TAG",questionData.choice4)
                        putSerializable(ARG_PARAM1,questionData)
                    }
                }
    }
}
