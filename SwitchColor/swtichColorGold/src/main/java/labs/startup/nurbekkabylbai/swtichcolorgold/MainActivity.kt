package labs.startup.nurbekkabylbai.swtichcolorgold

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isButtonBlue = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setBackgroundColor(Color.BLUE)
        parentLayout.setBackgroundColor(Color.RED)

        btn.setOnClickListener {
            parentLayout.setBackgroundColor(if(isButtonBlue) Color.BLUE else Color.RED)

            btn.setBackgroundColor(if(isButtonBlue) Color.RED else Color.BLUE)

            isButtonBlue = !isButtonBlue
        }
    }
}
