package labs.startup.nurbekkabylbai.quizzappgold

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Nurbek Kabylbay on 21.02.2018.
 */
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            startButton.text = "Пройти тест еще раз"

            resultTextView.visibility = View.VISIBLE
            val resultText = "Результат: " + data!!.getStringExtra("result")
            resultTextView.text = resultText

            val s =  data.getIntExtra("seconds", 0)
            timeTextView.visibility = View.VISIBLE
            val timeText = "Время: ${getFormattedString(s/360)}:${getFormattedString(s/60%60)}:${getFormattedString(s%60)}"
            timeTextView.text = timeText
        }
    }

    private fun getFormattedString(value: Int): String {
        return String.format("%02d", value)
    }
}
