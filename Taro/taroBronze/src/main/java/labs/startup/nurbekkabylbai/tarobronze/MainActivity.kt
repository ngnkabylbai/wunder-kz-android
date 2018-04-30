package labs.startup.nurbekkabylbai.tarobronze

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val prof = arrayOf("Архитектор", "Спасатель", "Генетик", "Пожарный", "Космонавт", "Хирург",
            "Врач", "Частный детектив", "Следователь")
    private val img = arrayOf(R.drawable.architecture, R.drawable.helper, R.drawable.genetic,
            R.drawable.fireman, R.drawable.spaceman, R.drawable.surger, R.drawable.doctor,
            R.drawable.detective, R.drawable.sledovatel)

    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
             val num = random.nextInt(20)
            startButton.isEnabled = false

            object : CountDownTimer((num*100+1).toLong(), 50L) {
                override fun onFinish() {
                    startButton.isEnabled = true
                }

                override fun onTick(millisUntilFinished: Long) {
                    val profIndex = random.nextInt(prof.size - 1)
                    professionTextView.text = prof[profIndex]
                    professionImageView.setBackgroundResource(img[profIndex])
                }
            }.start()
        }
    }
}
