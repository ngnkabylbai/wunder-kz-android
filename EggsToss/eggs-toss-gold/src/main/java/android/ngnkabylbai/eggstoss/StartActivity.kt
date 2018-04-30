package android.ngnkabylbai.eggstoss

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*

/**
 * Created by Nurbek Kabylbay on 10.03.2018.
 */
class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        startButton.setOnClickListener {
            startActivity(Intent(this, DifficultyActivity::class.java))
            finish()
        }
    }
}