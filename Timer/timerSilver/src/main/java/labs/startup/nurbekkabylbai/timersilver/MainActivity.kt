package labs.startup.nurbekkabylbai.timersilver

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var timer: Timer
    private var isStarted = false
    private var globalMillis = 60000L
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
                            val secondsAndMinutes = (getFormattedString(globalMillis / 60000, true)+
                                    ":"+getFormattedString(globalMillis / 1000 % 60, false))

                            timerTextView.text = secondsAndMinutes
                            globalMillis -= period
                        }
                    }
                }, 0, period)
            } else {
                timer.cancel()
                timerStartPauseButton.text = "START"
            }

            isStarted = !isStarted
        }

        timerResetButton.setOnClickListener {
            timer.cancel()
            globalMillis = 60000L
            isStarted = false
            timerStartPauseButton.text = "START"
            timerTextView.text = "01:00"
        }
    }

    private fun getFormattedString(value: Long, isMinutes: Boolean): String {
        if(isMinutes && globalMillis < 0)
            return String.format("-%02d", Math.abs(value))
        return String.format("%02d", Math.abs(value))
    }
}
