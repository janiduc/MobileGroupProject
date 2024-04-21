package com.example.practicalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnGetStarted: Button = findViewById(R.id.loginButton)
        btnGetStarted.setOnClickListener {
            val input1: EditText = findViewById(R.id.username1)
            val input2: EditText = findViewById(R.id.password1)

            if (input1.text.toString() == "user" && input2.text.toString() == "12345") {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                val intent1 = Intent(this, SecondScreen::class.java)
                startActivity(intent1)
            }

            else {
                Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}