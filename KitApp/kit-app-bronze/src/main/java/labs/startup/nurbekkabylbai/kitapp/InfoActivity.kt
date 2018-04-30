package labs.startup.nurbekkabylbai.kitapp

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.LruCache
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_info.*

/**
 * Created by Nurbek Kabylbay on 04.03.2018.
 */
class InfoActivity: AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue
    private lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        mTitle.text = intent.getStringExtra("title")
        author.text = intent.getStringExtra("author")
        mDescription.text = intent.getStringExtra("description")
        val img = intent.getStringExtra("image")

        requestQueue = Volley.newRequestQueue(this)
        imageLoader = ImageLoader(requestQueue, object : ImageLoader.ImageCache {
            private val cache = LruCache<String, Bitmap>(20)
            override fun getBitmap(url: String?): Bitmap? {
                return cache.get(url)
            }

            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                cache.put(url, bitmap)
            }
        })

        coverImageView.setImageUrl(img, imageLoader)
    }
}