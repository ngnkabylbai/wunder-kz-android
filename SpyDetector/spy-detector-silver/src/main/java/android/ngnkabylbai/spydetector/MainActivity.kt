package android.ngnkabylbai.spydetector

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val colors = arrayOf(Color.RED, Color.GRAY, Color.BLUE, Color.BLACK, Color.GREEN)
    private val colorNames =  arrayOf("Красный", "Серый", "Синий", "Чёрный", "Зелёный")

    private var leftColorIndex = 0
    private var rightColorIndex = 0
    private var leftTextIndex = 0
    private var rightTextIndex = 0

    private var correctAnswerCount = 0

    private lateinit var countdown: CountDownTimer
    private var isGameRunning = true
    private val seconds = 30000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countdown = object : CountDownTimer(seconds, 1) {
            override fun onFinish() {
                isGameRunning = false
            }

            override fun onTick(millisUntilFinished: Long) {
                countdownTextView.text = (millisUntilFinished/1000).toString()
            }
        }.start()

        yesButton.setOnClickListener {
            if(leftTextIndex == rightColorIndex) {
                onAnswer(true, "Правильно")
            } else {
                onAnswer(false, "Неправильно")
            }
        }

        noButton.setOnClickListener {
            if(leftTextIndex != rightColorIndex)  {
                onAnswer(true, "Правильно")
            } else {
                onAnswer(false, "Неправильно")
            }
        }

        updateView()
    }

    private fun onAnswer(isCorrect: Boolean, message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        if(isCorrect) {
            correctAnswerCount++
        }

        if(isGameRunning) {
            updateView()
        } else {
            val intent = Intent()
            intent.putExtra("score", correctAnswerCount.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun updateView() {
        val random = Random()

        leftTextIndex = random.nextInt(colorNames.size)
        leftTextView.text = colorNames[leftTextIndex]

        leftColorIndex = random.nextInt(colorNames.size)
        leftTextView.setTextColor(colors[leftColorIndex])

        rightTextIndex = random.nextInt(colorNames.size)
        rightTextView.text = colorNames[rightTextIndex]

        rightColorIndex = random.nextInt(colorNames.size)
        rightTextView.setTextColor(colors[rightColorIndex])
    }

}
