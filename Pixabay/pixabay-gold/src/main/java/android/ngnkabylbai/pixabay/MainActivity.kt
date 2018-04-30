package android.ngnkabylbai.pixabay

import android.content.Intent
import android.net.Uri
import android.ngnkabylbai.pixabay.activity.FavoritesActivity
import android.ngnkabylbai.pixabay.activity.VideoSearchActivity
import android.ngnkabylbai.pixabay.models.PixabayImage
import android.ngnkabylbai.pixabay.adapter.PixabayImageAdapter
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
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val TAG = "PIXABAY"
    private lateinit var requestQueue: RequestQueue
    private lateinit var gridLayoutManager: GridLayoutManager

    private var pixabayImages = ArrayList<PixabayImage>()
    private lateinit var adapter: PixabayImageAdapter

    private var page = 1
    private var searchFor = "car"
    private var pastLoaded = 0
    private var visibleLoaded = 0
    private var countLoaded = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)

        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager

        adapter = PixabayImageAdapter(this, pixabayImages)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if(dy > 0) {
                    pastLoaded = gridLayoutManager.childCount
                    visibleLoaded = gridLayoutManager.itemCount
                    countLoaded = gridLayoutManager.findFirstVisibleItemPosition()

                    if((visibleLoaded + pastLoaded) >= countLoaded) {
                        loadImages(searchFor, ++page)
                    }
                }
            }
        })

        videosButton.setOnClickListener {
            startActivity(Intent(this, VideoSearchActivity::class.java))
            finish()
        }

        loadImages(searchFor, page)
    }

    private fun loadImages(searchFor:String, page: Int) {
        val url = "https://pixabay.com/api/?key=8468591-cbb727cadd2cd8cef77fd9983&q=${Uri.encode(searchFor)}&image_type=photo&page=$page"

        Log.d(TAG, url)

        val request = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener<JSONObject> { response ->
                    Log.d(TAG, "Success: $response")
                    try {
                        val responseArray = response.getJSONArray("hits")
                        for (i in 0 until responseArray.length()) {
                            val obj = responseArray.getJSONObject(i)
                            pixabayImages.add(PixabayImage(
                                    obj.getString("tags"),
                                    obj.getString("webformatURL"),
                                    obj.getString("user"),
                                    obj.getString("pageURL"),
                                    obj.getInt("favorites"),
                                    obj.getInt("likes"),
                                    obj.getInt("comments")))
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, e.message)
                    }

                    val position = pixabayImages.size
                    adapter.notifyItemRangeInserted(position, position + 20)
                },
                Response.ErrorListener { error ->
                    Log.d(TAG, "Request error: ${error.message}")
                }
        )

        requestQueue.add(request)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                recyclerView.removeAllViewsInLayout()
                pixabayImages.clear()
                searchFor = newText
                page = 1
                adapter.clear()
                loadImages(searchFor, page)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })

        menu.findItem(R.id.favorites).setOnMenuItemClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
            true
        }

        return super.onCreateOptionsMenu(menu)
    }
}
