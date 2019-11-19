package yut.fall2019.esaat

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_review.*

class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        val sharedPref: SharedPreferences =  getSharedPreferences("ESAaT", 0)
        val depressionMemory = sharedPref.getString("depressionMemory",null)
        val anxietyMemory = sharedPref.getString("anxietyMemory",null)
        val depressionList = parseList(depressionMemory)
        val anxietyList = parseList(anxietyMemory)

        Log.d("TAG","Depression list is $depressionList")
        Log.d("TAG","Anxiety list is $anxietyList")
        val dSeries = arrayListOf<DataPoint>()
        val aSeries = arrayListOf<DataPoint>()
        val dIterator = depressionList.iterator()
        for ((index, value) in dIterator.withIndex()) {
            Log.d("TAG","index is $index, value is $value")
            dSeries.add(DataPoint(index.toDouble(),value.toDouble()))
            //println("The element at $index is $value")
        }
        val aIterator = anxietyList.iterator()
        for ((index, value) in aIterator.withIndex()) {
            aSeries.add(DataPoint(index.toDouble(),value.toDouble()))
            println("The element at $index is $value")
        }

        val dGraph = findViewById<GraphView>(R.id.depression_graph)
        val aGraph = findViewById<GraphView>(R.id.anxiety_graph)
        val series = LineGraphSeries<DataPoint>(arrayOf(DataPoint(0.0, 1.0), DataPoint(1.0, 5.0), DataPoint(2.0, 3.0), DataPoint(3.0, 2.0), DataPoint(4.0, 6.0)))
        val dData = LineGraphSeries<DataPoint>(dSeries.toTypedArray())
        val aData = LineGraphSeries<DataPoint>(aSeries.toTypedArray())

        dGraph.addSeries(dData)
        aGraph.addSeries(aData)
    }

    private fun parseList(string: String?): List<String> {
        return string!!.split(" ").map {  it.trim() }.dropLast(1)
    }
}
