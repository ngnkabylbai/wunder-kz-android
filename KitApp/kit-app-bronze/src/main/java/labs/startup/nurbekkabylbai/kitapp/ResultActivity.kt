package labs.startup.nurbekkabylbai.kitapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_result.*
import org.json.JSONArray
import org.json.JSONException


/**
 * Created by Nurbek Kabylbay on 04.03.2018.
 */
class ResultActivity: AppCompatActivity() {

    private val TAG = "TAG"
    private lateinit var books: JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val bookName = intent.getStringExtra("book_name")
        onSearchClick(bookName)
        bookNameEditText.setText(bookName)

        search.setOnClickListener {
            val bookName = bookNameEditText.text.toString()
            if(bookName == "") {
                Toast.makeText(this, "Введите название книги", Toast.LENGTH_SHORT).show()
            } else {
                onSearchClick(bookName)
            }
        }

    }

    private fun onSearchClick(bookName: String) {
        val url = "https://www.googleapis.com/books/v1/volumes?q=${bookName.replace(" ", "%20")}"
        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
                        Response.Listener { response ->
                    try {
                        Log.d("TEST", response.toString())
                        books = response.getJSONArray("items")
                        loadBooksToAdapter(books)
                        noResultImageView.visibility = View.INVISIBLE
                        resultListView.visibility = View.VISIBLE
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.d("TEST", e.message)
                        noResultImageView.visibility = View.VISIBLE
                        resultListView.visibility = View.INVISIBLE
                    }

                }, Response.ErrorListener { error ->
                    Log.d(TAG, error.message)
                }
        )

        queue.add(request)
    }

    private fun loadBooksToAdapter(books: JSONArray) {
        val adapter = BooksAdapter(this, books)
        resultListView.adapter = adapter
        val intent = Intent(this, InfoActivity::class.java)
        resultListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            try {
                Log.d("TEST", "onItemClicked:$position")
                val book = books.getJSONObject(position)
                val volumeInfo = book.getJSONObject("volumeInfo")

                intent.putExtra("title", volumeInfo.getString("title"))
                intent.putExtra("author", volumeInfo.getString("authors"))
                intent.putExtra("description", volumeInfo.getString("description"))

                val imageLinks = volumeInfo.getJSONObject("imageLinks")
                val thumbnailLink = imageLinks.getString("thumbnail")
                intent.putExtra("image", thumbnailLink)

                startActivity(intent)

            } catch (e: JSONException) {
                Log.d("TEST", "onItemClicked: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}