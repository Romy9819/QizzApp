package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        findViewById<Button>(R.id.averageBtn).setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        })
        findViewById<Button>(R.id.easyBtn).setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        })
        findViewById<Button>(R.id.hardBtn).setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        })

    }
}