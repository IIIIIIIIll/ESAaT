package yut.fall2019.esaat

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EndFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EndFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EndFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private var context: FragmentActivity? =null
    private var feedBack: TextView? = null
    private var title: TextView? = null
    private var ok:Button?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_end, container, false)
        feedBack= v.findViewById(R.id.feedBackText)
        title=v.findViewById(R.id.title)
        ok=v.findViewById(R.id.finishButton)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context = activity
        ok!!.setOnClickListener{(context as QuestionActivity).surveyCompleted(0)}
        val sharedPref: SharedPreferences =  context!!.getSharedPreferences("ESAaT", 0)
        val depressionScore:Int = sharedPref.getInt("dScore",-1)
        val anxietyScore : Int = sharedPref.getInt("aScore",-1)
        //TODO save the score into long term memory
        var dList = sharedPref.getString("depressionMemory", "")
        var aList = sharedPref.getString("anxietyMemory", "")
        dList = dList!!.plus("$depressionScore ")
        aList = aList!!.plus("$anxietyScore ")
//        Log.d("TAG",dList)
//        Log.d("TAG",aList)
        val editor = sharedPref.edit()
        editor.putString("depressionMemory",dList)
        editor.putString("anxietyMemory",aList)
        editor.apply()
        var text = "Your depression score is $depressionScore and your anxiety score is $anxietyScore. \n"
        text += when (depressionScore) {
            0 -> "Based on your responses to questions 1-8, you're not experiencing many of the symptoms seen in depression. However, if you have any concerns about your health or mood, please seek consultant. \n"
            1,2,3,4 -> "Based on your responses to questions 1-8, you're experiencing some of the symptoms seen in minimal depression. However, if you have any concerns about your health or mood, please seek consultant. \n"
            5,6,7,8,9 -> "Based on your responses to questions 1-8, you're experiencing some of the symptoms seen in mild depression. However, if you have any concerns about your health or mood, please seek consultant. \n"
            10,11,12,13,14 -> "Based on your responses to questions 1-8, you're experiencing some of the symptoms seen in moderate depression. Please consider seeking consultant on your mood and health. \n"
            15,16,17,18,19 -> "Based on your responses to questions 1-8, you're experiencing some of the symptoms seen in moderately severe depression.Please seeking consultant on your mood and health immediately. \n"
            20,21,22,23,24 -> "Based on your responses to questions 1-8, you're experiencing some of the symptoms seen in severe depression. Please seeking consultant on your mood and health immediately or call 800-950-NAMI (6264). \n"
            else -> "Something is wrong with depression analysis\n"
        }
        text += when (anxietyScore) {
            0,1,2,3,4 -> "Your anxiety score shows you have little or none symptoms of anxiety."
            5,6,7,8,9 -> "Your anxiety score shows you have mild symptoms of anxiety. Please continue monitoring your mood status"
            10,11,12,13,14 -> "Your anxiety score shows you have little or none symptoms of anxiety."
            15,16,17,18,19,20,21-> "Your anxiety score shows you have little or none symptoms of anxiety."
            else -> "Something is wrong with anxiety analysis"
        }
        //feedBack!!.text.set (TypedValue.COMPLEX_UNIT_SP, 16f)
        title!!.text = getString(R.string.EndTitle)
        feedBack!!.text = text
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
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
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EndFragment.
         */
        @JvmStatic
        fun newInstance() =
                EndFragment().apply {
                }
    }
}
