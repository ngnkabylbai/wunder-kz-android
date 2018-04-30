package labs.startup.nurbekkabylbai.kitapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateFilterArray = resources.getStringArray(R.array.dateFilter)
        val typeFilterArray = resources.getStringArray(R.array.typeFilter)

        val dateAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dateFilterArray)
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val typeAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeFilterArray)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        dateFilterSpinner.adapter = dateAdapter
        typeFilterSpinner.adapter = typeAdapter

        search.setOnClickListener {
            if(bookNameEditText.text.isNotEmpty()) {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("book_name", bookNameEditText.text.toString())
                intent.putExtra("date_filter_position", dateFilterSpinner.selectedItemPosition)
                intent.putExtra("type_filter_position", typeFilterSpinner.selectedItemPosition)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Введите название книги", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
