package android.ngnkabylbai.spydetector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_start.*

/**
 * Created by Nurbek Kabylbay on 13.03.2018.
 */
class StarterActivity : AppCompatActivity() {

    private val realm = Realm.getDefaultInstance()
    private val query = realm.where(Record::class.java)

    private val adapter = Adapter(this)
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        loadRecyclerViewData()

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
        if(resultCode == Activity.RESULT_OK  && data != null) {
            val score = data.getIntExtra("score", -1)

            resultTextView.text = "Результат: $score"
            resultTextView.visibility = View.VISIBLE

            insertOrUpdateDB(score)
            loadRecyclerViewData()
        }
    }

    private fun insertOrUpdateDB(score: Int) {
        var oldRecord: Record? = null
        realm.executeTransaction { realm ->
            oldRecord = realm.where(Record::class.java)
                             .equalTo("name", nickEditText.text.toString())
                             .findFirst()
        }

        if(oldRecord == null || (oldRecord != null && score > oldRecord!!.score)) {
            val newRecord = Record()
            newRecord.name = nickEditText.text.toString()
            newRecord.score = score

            realm.executeTransaction { realm -> realm.insertOrUpdate(newRecord) }
        }
    }

    private fun loadRecyclerViewData() {
        val allValues = query
                        .findAll()
                        .sort("score", Sort.DESCENDING)
        adapter.loadData(allValues)
    }
}
