package yut.fall2019.esaat

import com.google.gson.annotations.SerializedName

class QuestionModel {
    @SerializedName("Questions")
    var questions = arrayListOf<Question>()

    class Question {
        @SerializedName("Title")
        var Title: String? = null
        @SerializedName("Choice1")
        var Choice1: String? = null
        @SerializedName("Choice2")
        var Choice2: String? = null
        @SerializedName("Choice3")
        var Choice3: String? = null
        @SerializedName("Choice4")
        var Choice4: String? = null
    }
}