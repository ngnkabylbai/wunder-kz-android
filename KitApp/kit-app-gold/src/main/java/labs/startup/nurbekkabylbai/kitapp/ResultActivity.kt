package labs.startup.nurbekkabylbai.kitapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
class ResultActivity: AppCompatActivity(), AbsListView.OnScrollListener {

    private val TAG = "TAG"
    private lateinit var books: JSONArray
    private lateinit var adapter: BooksAdapter

    private lateinit var bookName: String
    private var dateFilter = "relevance"
    private var typeFilter = "all"
    private var preLast = 0
    private var startIndex = 0
    private val maxResults = 10

    private var timeToCleanAdapter = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        bookName = intent.getStringExtra("book_name")
        val dateFilterPosition = intent.getIntExtra("date_filter_position", 0)
        val typeFilterPosition = intent.getIntExtra("type_filter_position", 0)

        val dateFilterArray = resources.getStringArray(R.array.dateFilter)
        val typeFilterArray = resources.getStringArray(R.array.typeFilter)

        val dateAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dateFilterArray)
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val typeAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeFilterArray)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        dateFilterSpinner.adapter = dateAdapter
        typeFilterSpinner.adapter = typeAdapter

        dateFilterSpinner.setSelection(dateFilterPosition)
        typeFilterSpinner.setSelection(typeFilterPosition)

        dateFilterSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                dateFilter = when(position) {
                    1 -> "newest"
                    else -> "relevance"
                }
                timeToCleanAdapter = true
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

        typeFilterSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                typeFilter = when(position) {
                    1 -> "books"
                    2 -> "magazines"
                    else -> "all"
                }
                timeToCleanAdapter = true
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

        bookNameEditText.setText(bookName)
        onSearchClick(bookName)

        search.setOnClickListener {
            timeToCleanAdapter = true
            val bookName = bookNameEditText.text.toString()
            if(bookName == "") {
                Toast.makeText(this, "Введите название книги", Toast.LENGTH_SHORT).show()
            } else {
                onSearchClick(bookName)
            }
        }

        adapter = BooksAdapter(this)
        resultListView.adapter = adapter
        resultListView.setOnScrollListener(this)
    }


    override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        when (view.id) {
            R.id.resultListView -> {
                val lastItem = firstVisibleItem + visibleItemCount

                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        startIndex = lastItem
                        Log.d("TEST", "making new request...")
                        preLast = lastItem
                        onSearchClick(bookName)
                    }
                }
            }
        }
    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) { }

    private fun onSearchClick(bookName: String) {
        val url = "https://www.googleapis.com/books/v1/volumes?q=${bookName.replace(" ", "%20")}&printType=$typeFilter&orderBy=$dateFilter&startIndex=$startIndex&maxResults=$maxResults"
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
        if(timeToCleanAdapter) {
            adapter.clear()
            timeToCleanAdapter = false
        }
        adapter.loadNewBooks(books)
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