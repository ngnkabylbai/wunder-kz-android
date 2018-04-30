package android.ngnkabylbai.spydetector

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
    private var inCorrectAnswerCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        } else {
            inCorrectAnswerCount++
        }

        updateView()
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
