package android.ngnkabylbai.pixabay.activity

import android.content.Intent
import android.net.Uri
import android.ngnkabylbai.pixabay.MainActivity
import android.ngnkabylbai.pixabay.R
import android.ngnkabylbai.pixabay.adapter.PixabayVideoAdapter
import android.ngnkabylbai.pixabay.models.PixabayVideo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_video_search.*
import org.json.JSONObject

/**
 * Created by Nurbek Kabylbay on 24.03.2018.
 */
class VideoSearchActivity : AppCompatActivity() {
    private val TAG = "PIXABAY"
    private lateinit var requestQueue: RequestQueue
    private lateinit var gridLayoutManager: GridLayoutManager

    private var pixabayVideos= ArrayList<PixabayVideo>()
    private lateinit var adapter: PixabayVideoAdapter

    private var page = 1
    private var searchFor = "car"
    private var pastLoaded = 0
    private var visibleLoaded = 0
    private var countLoaded = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_search)

        requestQueue = Volley.newRequestQueue(this)

        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager

        adapter = PixabayVideoAdapter(this, pixabayVideos)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if(dy > 0) {
                    pastLoaded = gridLayoutManager.childCount
                    visibleLoaded = gridLayoutManager.itemCount
                    countLoaded = gridLayoutManager.findFirstVisibleItemPosition()

                    if((visibleLoaded + pastLoaded) >= countLoaded) {
                        loadVideos(searchFor, ++page)
                    }
                }
            }
        })

        loadVideos(searchFor, page)
    }

    private fun loadVideos(searchFor: String, page: Int) {
        val url = "https://pixabay.com/api/videos/?key=8468591-cbb727cadd2cd8cef77fd9983&q=${Uri.encode(searchFor)}&page=$page"

        Log.d(TAG, url)

        val request = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener<JSONObject> { response ->
                    Log.d(TAG, "Success: $response")
                    try {
                        val responseArray = response.getJSONArray("hits")
                        for (i in 0 until responseArray.length()) {
                            val obj = responseArray.getJSONObject(i)
                            pixabayVideos.add(PixabayVideo(
                                    obj.getString("tags"),
                                    obj.getString("picture_id"),
                                    obj.getJSONObject("videos").getJSONObject("tiny").getString("url"),
                                    obj.getString("user"),
                                    obj.getString("pageURL"),
                                    obj.getInt("views"),
                                    obj.getInt("likes"),
                                    obj.getInt("favorites")))
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "Error:${e.message}")
                    }

                    val position = pixabayVideos.size
                    adapter.notifyItemRangeInserted(position, position + 20)
                },
                Response.ErrorListener { error ->
                    Log.d(TAG, "Request error: ${error.message}")
                }
        )

        imagesButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        requestQueue.add(request)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
         menuInflater.inflate(R.menu.search, menu)

        val searchView =  menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                recyclerView.removeAllViewsInLayout()
                pixabayVideos.clear()
                searchFor = newText
                page = 1
                adapter.clear()
                loadVideos(searchFor, page)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}