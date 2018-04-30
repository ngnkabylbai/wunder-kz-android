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
class CarActivity: AppCompatActivity() {

    private val prof = arrayOf("Jeep", "Honda", "Wolsvagen", "Daewoo")
    private val img = arrayOf(R.drawable.car1, R.drawable.car2, R.drawable.car3,
            R.drawable.car4)

    private val random = Random()
    private val handler = Handler()
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

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