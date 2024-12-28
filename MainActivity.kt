package com.example.voicecommandapp

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private val REQUEST_CODE_SPEECH_INPUT = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)
        val startListeningButton: Button = findViewById(R.id.startListeningButton)

        // Start voice recognition
        startListeningButton.setOnClickListener {
            startVoiceCommand()
        }
    }

    private fun startVoiceCommand() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception) {
            Toast.makeText(this, "Your device does not support voice input!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            resultTextView.text = result
            processCommand(result)
        }
    }

    private fun processCommand(command: String?) {
        if (command != null) {
            when {
                command.contains("send message", true) -> {
                    Toast.makeText(this, "Simulating sending a message...", Toast.LENGTH_SHORT).show()
                    // This is where you'd integrate with Accessibility Service or an app
                }
                else -> Toast.makeText(this, "Command not recognized!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
