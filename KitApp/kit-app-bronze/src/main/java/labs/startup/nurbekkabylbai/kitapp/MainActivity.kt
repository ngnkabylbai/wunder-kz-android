package labs.startup.nurbekkabylbai.kitapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("book_name", bookNameEditText.text.toString())
            startActivity(intent)
        }
    }
}
