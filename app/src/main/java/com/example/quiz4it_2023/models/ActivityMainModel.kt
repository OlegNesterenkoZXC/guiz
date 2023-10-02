package com.example.quiz4it_2023.models

import androidx.lifecycle.ViewModel
import com.example.quiz4it_2023.R
import com.example.quiz4it_2023.data.Question

class ActivityMainModel : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_africa, true),
        Question(R.string.question_asia, true),
    )

     var currentIndex = 0
    var isCheater = false

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
        isCheater = false
    }

    fun moveToPrev() {
        currentIndex = (questionBank.size + currentIndex - 1) % questionBank.size
    }
}