package labs.startup.nurbekkabylbai.tarogold

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prof.setOnClickListener {
            startActivity(Intent(this, ProfessionActivity::class.java))
        }

        payment.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }

        car.setOnClickListener {
            startActivity(Intent(this, CarActivity::class.java))
        }
    }
}
