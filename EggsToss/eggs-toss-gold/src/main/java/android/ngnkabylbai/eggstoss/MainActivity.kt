package android.ngnkabylbai.eggstoss

import android.content.Intent
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var leftPos = 0f
    private var centerPos = 0f
    private var rightPos = 0f
    private var spawnPos = 0f
    private var eggPos = 0
    private var basketPos = 1
    private val random = Random()
    private lateinit var timer: CountDownTimer
    private var egg: ImageView? = null

    private var eggVelocity = 15
    private var maxLifeCount = 4
    private var lifeCount = 4
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val display = windowManager.defaultDisplay
        val sizePoint = Point()
        display.getSize(sizePoint)

        Log.d("TAG", basket.y.toString())

        when(intent.getStringExtra("difficulty")) {
            "medium" -> {
                val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                lp.setMargins(2, 2 , 2, 150)
                lp.addRule(RelativeLayout.ABOVE, R.id.navigationControl)
                basket.layoutParams = lp
            }
            "hard" -> {
                val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                lp.setMargins(2, 2 , 2, 250)
                lp.addRule(RelativeLayout.ABOVE, R.id.navigationControl)
                basket.layoutParams = lp
            }
            else -> { }
        }

        Log.d("TAG", basket.y.toString())

        val screenWidth = sizePoint.x
        val mConst = 50

        leftPos = (screenWidth / 6) - mConst.toFloat()
        centerPos = (screenWidth / 6)*3 - mConst.toFloat()
        rightPos = (screenWidth / 6)*5 - mConst.toFloat()

        moveLeft.setOnClickListener {
            basket.x = leftPos - basket.width / 4
            basketPos = 0
        }

        moveCenter.setOnClickListener {
            basket.x = centerPos - basket.width / 2
            basketPos = 1
        }

        moveRight.setOnClickListener {
            basket.x = rightPos - (basket.width / 4) * 3
            basketPos = 2
        }

        createEgg()
    }

    private fun createEgg() {
        parentView.removeView(egg)
        eggPos = random.nextInt(3)

        spawnPos = when(eggPos) {
            0 -> leftPos
            1 -> centerPos
            2 -> rightPos
            else -> leftPos
        }


        egg = ImageView(this)

        val superEggRandom = random.nextInt(100)
        if(superEggRandom in 40..65) { // creating a SUPER_EGG with a probability
            egg!!.setImageResource(R.drawable.defaultegg)
            egg!!.tag = "SUPER_EGG"
        } else {
            egg!!.setImageResource(R.drawable.egg)
        }

        parentView.addView(egg)
        egg!!.x = spawnPos
        egg!!.y = -50f
        toss()
    }

    private fun toss() {
        timer = object : CountDownTimer(4500, 1) {
            override fun onTick(millisUntilFinished: Long) {
                egg!!.y += eggVelocity
                if((egg!!.y >= basket.y) && (basketPos == eggPos)) {

                    if(egg!!.tag == "SUPER_EGG") { // add extra life if it's SUPER EGG
                        maxLifeCount = 5
                        lifeCount = ++lifeCount
                        if(lifeCount > maxLifeCount) lifeCount = maxLifeCount
                    }

                    if(++score % 5 == 0) { // increasing difficulty of the game
                        eggVelocity += 3
                    }

                    scoreTextView.text = (score).toString()
                    timer.cancel()
                    setLifeImage()
                    createEgg()
                } else if(egg!!.y >= navigationControl.y) {
                    lifeCount--
                    timer.cancel()
                    setLifeImage()

                    when(eggPos) {
                        0 -> leftBrokenEgg.visibility = View.VISIBLE
                        1 -> centerBrokenEgg.visibility = View.VISIBLE
                        2 -> rightBrokenEgg.visibility = View.VISIBLE
                    }

                    if(lifeCount < 0) {
                        val intent = Intent(this@MainActivity, FinishActivity::class.java)
                        intent.putExtra("score", score.toString())
                        startActivity(intent)
                        finish()
                    } else {
                        createEgg()
                    }
                }
            }

            override fun onFinish() {
                parentView.removeView(egg)
                createEgg()
            }
        }.start()
    }

    private fun setLifeImage() {
        eggsLayout.removeAllViews()
        for(i in maxLifeCount downTo 0) {
            if(lifeCount - i < 0) {
                createLifeImage(R.drawable.brokenegg) // broken egg
            } else {
                createLifeImage(R.drawable.defaultegg) // default egg
            }
        }
    }

    private fun createLifeImage(imageResource: Int) {
        val eggImage = ImageView(this)
        eggImage.setImageResource(imageResource)

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(2, 2 , 2,2)
        eggImage.layoutParams = lp
        eggsLayout.addView(eggImage)
    }
}
