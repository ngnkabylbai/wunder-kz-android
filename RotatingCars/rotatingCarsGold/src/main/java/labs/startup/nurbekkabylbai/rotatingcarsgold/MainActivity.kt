package labs.startup.nurbekkabylbai.rotatingcarsgold

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                val first: Drawable = firstImg.drawable
                val second: Drawable = secondImg.drawable
                val third: Drawable = thirdImg.drawable
                val fourth: Drawable = fourthImg.drawable

                firstImg.setImageDrawable(second)
                secondImg.setImageDrawable(third)
                thirdImg.setImageDrawable(fourth)
                fourthImg.setImageDrawable(first)

                handler.postDelayed(this, 1000)
            }
        }, 1000)
    }
}
