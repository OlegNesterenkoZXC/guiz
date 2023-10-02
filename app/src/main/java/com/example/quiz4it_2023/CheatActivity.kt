package com.example.quiz4it_2023

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

const val EXTRA_ANSWER_SHOW = "com.example.quiz4it_2023.answer_shown"
const val EXTRA_ANSWER_IS_TRUE = "com.example.quiz4it_2023.extra_is_true"
class CheatActivity : AppCompatActivity() {
    private lateinit var showAnswerButton: Button
    private lateinit var answerTextView: TextView
    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        answerIsTrue = intent?.getBooleanExtra("answer", false)?: false
        setContentView(R.layout.activity_cheat)
        showAnswerButton = findViewById(R.id.btnShow)
        answerTextView = findViewById(R.id.tvAnswer)
        showAnswerButton.setOnClickListener {
            answerTextView.text = if (answerIsTrue) "Да" else "Нет"
            answerTextView.visibility = View.VISIBLE
            showAnswerButton.visibility = View.GONE
            setAnswerShownResult(true)
        }
    }

    private fun setAnswerShownResult(isAnswerShow: Boolean=false) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOW, isAnswerShow)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}