package labs.startup.nurbekkabylbai.switchcolorsilver

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            val rand = Random()

            parentLayout.setBackgroundColor(Color.argb(255, 0, rand.nextInt(255), 0))

            btn.setBackgroundColor(Color.argb(255, 0,0, rand.nextInt(255)))
        }
    }
}
