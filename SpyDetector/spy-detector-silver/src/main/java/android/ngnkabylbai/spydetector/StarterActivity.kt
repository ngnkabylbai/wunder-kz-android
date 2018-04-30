package android.ngnkabylbai.spydetector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_start.*

/**
 * Created by Nurbek Kabylbay on 13.03.2018.
 */
class StarterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        startButton.setOnClickListener {
            if(nickEditText.text.isNotEmpty()) {
                startActivityForResult(Intent(this, MainActivity::class.java), 0)
            } else {
                Toast.makeText(this, "Введите ваше имя", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            val score = data?.getStringExtra("score")
            resultTextView.text = "Результат: $score"
            resultTextView.visibility = View.VISIBLE
        }
    }
}