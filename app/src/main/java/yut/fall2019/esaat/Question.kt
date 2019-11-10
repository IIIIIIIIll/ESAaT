package yut.fall2019.esaat

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class QuestionModel {
    @SerializedName("Questions")
    var questions = arrayListOf<Question>()

     class Question : Serializable {
        @SerializedName("title")
        var title: String? = null
        @SerializedName("choice1")
        var choice1: String? = null
        @SerializedName("choice2")
        var choice2: String? = null
        @SerializedName("choice3")
        var choice3: String? = null
        @SerializedName("choice4")
        var choice4: String? = null
    }
}