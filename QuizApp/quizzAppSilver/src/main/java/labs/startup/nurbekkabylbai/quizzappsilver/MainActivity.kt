package labs.startup.nurbekkabylbai.quizzappsilver

import android.content.DialogInterface
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val questions = arrayOf(
            "Кто является создателем сети Facebook?",
            "Какой мессенджер является самым популярным в мире?",
            "Какой из предложенных логотипов предналежит файловому хостингу?",
            "Какое число имеет вид 00000011 в двоичной системе?",
            "Какая операционная система самая популярная?",
            "Что не является языком программирования?"
    )

    private val pictures: Array<IntArray> = arrayOf(
            intArrayOf(R.drawable.answer11, R.drawable.answer12, R.drawable.answer13, R.drawable.answer14),
            intArrayOf(R.drawable.answer21, R.drawable.answer22, R.drawable.answer23, R.drawable.answer24),
            intArrayOf(R.drawable.answer31, R.drawable.answer32, R.drawable.answer33, R.drawable.answer34),
            intArrayOf(R.drawable.answer41, R.drawable.answer42, R.drawable.answer43, R.drawable.answer44),
            intArrayOf(R.drawable.answer51, R.drawable.answer52, R.drawable.answer53, R.drawable.answer54),
            intArrayOf(R.drawable.answer61, R.drawable.answer62, R.drawable.answer63, R.drawable.answer64)
    )

    private val rightAnswers = intArrayOf(R.drawable.answer11, R.drawable.answer23, R.drawable.answer32,
            R.drawable.answer43, R.drawable.answer52, R.drawable.answer63)

    private val images: ArrayList<ImageView> = ArrayList()

    private var currentQuestion = 0
    private var rightAnswerCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topLeftImageView.setOnClickListener { checkAnswer(topLeftImageView) }
        topRightImageView.setOnClickListener { checkAnswer(topRightImageView) }
        botLefttImageView.setOnClickListener { checkAnswer(botLefttImageView) }
        botRightImageView.setOnClickListener { checkAnswer(botRightImageView) }

        images.add(topLeftImageView)
        images.add(topRightImageView)
        images.add(botLefttImageView)
        images.add(botRightImageView)

        nextButton.setOnClickListener { setupNextQuestion() }
        nextButton.isEnabled = false
        setupNextQuestion()
    }

    private fun checkAnswer(answer: ImageView) {
        setImageClickable(false)
        nextButton.isEnabled = true
        if(answer.contentDescription == "answer") {
            answer.setBackgroundColor(Color.parseColor("#46ff97"))
            rightAnswerCount++
        } else {
            answer.setBackgroundColor(Color.parseColor("#ff6279"))
        }
    }

    private fun setupNextQuestion() {
        if(isTimeToEnd()) return

        setImageClickable(true)
        nextButton.isEnabled = false
        questionNumberTextView.text = "Вопрос ${currentQuestion+1}"
        questionTextView.text = questions[currentQuestion]

        for(i in 0 until 4) {
            val drawable = getDrawable(pictures[currentQuestion][i])
            images[i].setImageDrawable(drawable)
            images[i].setBackgroundColor(Color.parseColor("#00ffffff"))

            if(rightAnswers[currentQuestion] == pictures[currentQuestion][i]) {
                images[i].contentDescription = "answer"
            } else {
                images[i].contentDescription = ""
            }
        }
        currentQuestion++
    }

    private fun isTimeToEnd(): Boolean {
        if(currentQuestion >= questions.size){
            val builder = AlertDialog.Builder(this)
                    .setTitle("Thanks you")
                    .setMessage("you finished the test. Result: $rightAnswerCount/${questions.size}")
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        currentQuestion = 0
                        setupNextQuestion()
                    })
            builder.show()
            return true
        }
        return false
    }

    private fun setImageClickable(flag: Boolean) {
        topLeftImageView.isClickable = flag
        topRightImageView.isClickable = flag
        botLefttImageView.isClickable = flag
        botRightImageView.isClickable = flag
    }
}
