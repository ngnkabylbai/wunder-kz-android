package com.example.nurbekkabylbai.timer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var timer: Timer
    private var isStarted = false
    private var globalMillis = 0L
    private val period = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerStartPauseButton.setOnClickListener {
            if(!isStarted) {
                timer = Timer()
                timerStartPauseButton.text = "PAUSE"
                timer.scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            val secondsAndMinutes = (getFormattedString(globalMillis / 60000)+
                                    ":"+getFormattedString(globalMillis / 1000 % 60))

                            timerTextView.text = secondsAndMinutes
                            globalMillis += period
                        }
                    }
                }, 0, period/5)
            } else {
                timer.cancel()
                timerStartPauseButton.text = "START"
            }

            isStarted = !isStarted
        }

        timerResetButton.setOnClickListener {
            timer.cancel()
            globalMillis = 0
            isStarted = false
            timerStartPauseButton.text = "START"
            timerTextView.text = "00:00"
        }
    }

    private fun getFormattedString(value: Long): String {
        return String.format("%02d", value)
    }
}
