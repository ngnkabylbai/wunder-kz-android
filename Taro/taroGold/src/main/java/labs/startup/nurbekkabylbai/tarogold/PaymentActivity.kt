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
class PaymentActivity: AppCompatActivity() {

    private val prof = arrayOf("50000 UE", "1250000 KZT", "0 KZT", "12344440 KZT", "60000 PKE", "654123 KZT",
            "7542315 SKD", "11111 KZT", "50032165400 RU")
    private val img = arrayOf(R.drawable.money1, R.drawable.money2, R.drawable.money3,
            R.drawable.money4)

    private val random = Random()
    private val handler = Handler()
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        startButton.setOnClickListener {
            startButton.visibility = View.GONE
            stopButton.visibility = View.VISIBLE
            isRunning = true

            handler.postDelayed(object : Runnable {
                override fun run() {
                    val profIndex = random.nextInt(prof.size - 1)
                    professionTextView.text = prof[profIndex]

                    if(profIndex == 2) {
                        professionImageView.setBackgroundResource(R.drawable.empty)
                    } else {
                        professionImageView.setBackgroundResource(img[profIndex%img.size])
                    }

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