package com.example.quiz4it_2023

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract.Instances
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.quiz4it_2023.models.ActivityMainModel

private const val KEY_INDEX = "com.example.quiz4it_2023.index"
class MainActivity : AppCompatActivity() {
    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val viewModel: ActivityMainModel by lazy {
        var provider = ViewModelProvider(this)
        provider.get(ActivityMainModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX)?:0

        btnTrue = findViewById(R.id.btnTrue)
        btnFalse = findViewById(R.id.btnFalse)
        nextButton = findViewById(R.id.btnNext)
        prevButton = findViewById(R.id.btnPrev)
        questionTextView = findViewById(R.id.tvQuestion)

        updateQuestion()

        btnTrue.setOnClickListener { checkAnswer(true) }
        btnFalse.setOnClickListener { checkAnswer(false) }
        nextButton.setOnClickListener { viewModel.moveToNext(); updateQuestion() }
        prevButton.setOnClickListener { viewModel.moveToPrev(); updateQuestion() }

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    viewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOW, false)?: false
                }
            }

        val cheatButton: Button = findViewById(R.id.btnCheat)

        cheatButton.setOnClickListener {
//            val intent = Intent(this, CheatActivity::class.java)
//            intent.putExtra("answer", viewModel.currentQuestionAnswer)
//            startActivity(intent)
            val intent = CheatActivity.newIntent(
                this@MainActivity,
                viewModel.currentQuestionAnswer
            )
            resultLauncher.launch(intent)
        }
    }

    private fun updateQuestion() {
        val questionTextResId = viewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = viewModel.currentQuestionAnswer
        val messageResId = if (viewModel.isCheater) {
            R.string.judgement_toast
        } else if ( userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEX, viewModel.currentIndex)
    }
}