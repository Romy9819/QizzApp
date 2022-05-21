package com.example.quizapp

class Models {
    class AllResults (val results: List<ResultFeed>,
                        val response_code: Int
    )

    class QuizResults(
        val results: String
    )
    class ResultFeed(
        val category: String,
        val type: String,
        val difficulty: String,
        val question: String,
        val correct_answer: String,
        val incorrect_answers: ArrayList<String>
    )
    class JoinedFeed(
        val questions: ArrayList<String>,
        val answers: ArrayList<ArrayList<String>>,
        val correct_answers: ArrayList<String>
    )
    class DoneFeed(
        val qNumber: String,
        val qCorrectAnswers: String,
        val qAttempted: String,
        val qNegative: String
    )
}