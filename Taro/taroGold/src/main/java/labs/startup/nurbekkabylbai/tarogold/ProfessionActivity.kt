package labs.startup.nurbekkabylbai.tarogold

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_prof.*
import java.util.*

/**
 * Created by Nurbek Kabylbay on 18.02.2018.
 */
class ProfessionActivity: AppCompatActivity() {

    private val prof = arrayOf("Архитектор", "Спасатель", "Генетик", "Пожарный", "Космонавт", "Хирург",
            "Врач", "Частный детектив", "Следователь")
    private val img = arrayOf(R.drawable.architecture, R.drawable.helper, R.drawable.genetic,
            R.drawable.fireman, R.drawable.spaceman, R.drawable.surger, R.drawable.doctor,
            R.drawable.detective, R.drawable.sledovatel)

    private val random = Random()
    private val handler = Handler()
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prof)

        startButton.setOnClickListener {
            startButton.visibility = View.GONE
            stopButton.visibility = View.VISIBLE
            isRunning = true

            handler.postDelayed(object : Runnable {
                override fun run() {
                    val profIndex = random.nextInt(prof.size - 1)
                    professionTextView.text = prof[profIndex]
                    professionImageView.setBackgroundResource(img[profIndex])
                    if(isRunning)
                        handler.postDelayed(this, 50)
                }
            }, 50)
        }

        stopButton.setOnClickListener {
            startButton.visibility = View.VISIBLE
            stopButton.visibility = View.GONE
            isRunning = false
        }
    }
}