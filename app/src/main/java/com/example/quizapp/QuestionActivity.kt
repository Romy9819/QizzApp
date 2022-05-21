package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.GsonBuilder
import java.lang.Math.abs

class QuestionActivity : AppCompatActivity() {
    companion object {
        var allJoined: ArrayList<Models.JoinedFeed> = ArrayList()
        var selectedAnswers: ArrayList<String> = ArrayList()
        var questionNo: Int = 0
        var isCorrect: Int = 0
        var isFailed: Int = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("checking", "hi hello")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        supportActionBar?.hide()

        Log.d("checking", "hi hello")

        val endpoint: String = "https://opentdb.com/api.php?amount=10&type=multiple"
        val questions: ArrayList<String> = ArrayList()
        val allAnswers: ArrayList<ArrayList<String>> = ArrayList()
        val allCorrectAnswers: ArrayList<String> = ArrayList()

        val scoreBoard: ConstraintLayout = findViewById(R.id.display_layout)
        scoreBoard.visibility = View.GONE

        Log.d("yo", "hi hello")
        val httpAsync = endpoint.httpGet()
            .responseString{request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        println("HEY ROMY NOT WORKING "+ex)
                        Log.d("yo", "hey bro "+ex.toString())
                    }
                    is Result.Success -> {
                        val data: String = result.get()
                        println("HEY ROMY WORKING "+data)
                        Log.d("yo", data)
                        val gsonBuilder = GsonBuilder().create()

                        val mainData: Models.AllResults = gsonBuilder.fromJson(data, Models.AllResults::class.java)
                        for ((index, value) in mainData.results.withIndex()) {
                            val mainFeed = mainData.results
                            val question = mainFeed[index].question
                            questions.add(question)

                            val answers = mainFeed[index].incorrect_answers
                            answers.add((0..3).random(), mainFeed[index].correct_answer)
                            allAnswers.add(answers)

                            val canswers = mainFeed[index].correct_answer
                            allCorrectAnswers.add(canswers)
                        }
                    }
                }
            }
        httpAsync.join()

        allJoined.add(
            Models.JoinedFeed(
                questions = questions,
                answers = allAnswers,
                correct_answers = allCorrectAnswers
            )
        )
        startQuiz()
    }

    private fun startQuiz() {
        val nextBtn = findViewById<ImageButton>(R.id.nextBtn)
        val totalNum: TextView = findViewById<TextView>(R.id.question_count)
        val mainQuestion: TextView = findViewById(R.id.main_question)
        val scoreLayout: ConstraintLayout = findViewById(R.id.display_layout)
        val quizLayout: ConstraintLayout = findViewById(R.id.quiz)
        val scoreDialog: ListView = findViewById(R.id.done_pop)
        var score = findViewById<TextView>(R.id.score)

        var questionNum = questionNo

        val currentQuestion = allJoined[0].questions[questionNo]

        val answerListView: ListView = findViewById(R.id.answers_container)

        questionNum++
        totalNum.text = "${questionNum.toString()}/${allJoined[0].questions.count()}"

        mainQuestion.text  = currentQuestion

        var qanswers: ArrayList<String> = allJoined[0].answers[questionNo]
        setAnswers(qanswers)

        answerListView.setOnItemClickListener {parent, view, position, id ->
            val clickedId = id.toInt()
            val correctAnswer = allJoined[0].correct_answers[questionNo]
            val selectedAnswer = allJoined[0].answers[questionNo][clickedId]
            val answerIsCorrect = selectedAnswer == correctAnswer
            var finalScore = 0

            if (answerIsCorrect) {
                finalScore += 10
                isCorrect++
            } else {
                isFailed--
            }

            if (questionNo == allJoined[0].questions.count() -1 && questionNum === 10) {
                quizLayout.visibility = View.GONE
                scoreLayout.visibility = View.VISIBLE

                val info: Models.DoneFeed = Models.DoneFeed(
                    qNumber = "${allJoined[0].questions.count()}",
                    qCorrectAnswers = "${finalScore}",
                    qAttempted = "${10}",
                    qNegative = "${abs(isFailed)}"
                )
                scoreDialog.adapter = DoneAdapter(this, info)
            } else {
                questionNum++
                questionNo++
            }

            totalNum.text = "${questionNum.toString()}/${allJoined[0].questions.count()}"
            mainQuestion.text = allJoined[0].questions[questionNo];

            //update answers
            val newAnswers = allJoined[0].answers[questionNo];
            setAnswers(newAnswers)
        }
    }

    private fun setAnswers(qanswers: ArrayList<String>) {
        val answers: ListView = findViewById(R.id.answers_container)
        for ((index, value) in qanswers.withIndex()) {
            answers.adapter = AnswerAdapter(this, qanswers)
        }
    }
}