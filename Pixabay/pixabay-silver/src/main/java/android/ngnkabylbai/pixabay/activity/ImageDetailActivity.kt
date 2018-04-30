package android.ngnkabylbai.pixabay.activity

import android.ngnkabylbai.pixabay.R
import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_image_detail.*

/**
 * Created by Nurbek Kabylbay on 24.03.2018.
 */
class ImageDetailActivity: AppCompatActivity() {

    private lateinit var tags: String
    private lateinit var webformatURL: String
    private lateinit var user: String
    private lateinit var pageURL: String
    private var favorites = 0
    private var likes = 0
    private var comments = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        tags = intent.getStringExtra("tags")
        user = intent.getStringExtra("user")
        webformatURL = intent.getStringExtra("webformatURL")
        pageURL = intent.getStringExtra("pageURL")
        likes = intent.getIntExtra("likes", 0)
        comments = intent.getIntExtra("comments", 0)
        favorites = intent.getIntExtra("favorites", 0)

        authorTextView.text = user
        tagsTextView.text = tags
        likeCountTextView.text = likes.toString()
        favCountTextView.text = favorites.toString()
        commentCountTextView.text = comments.toString()

        Glide.with(this)
                .load(webformatURL)
                .apply(RequestOptions().placeholder(R.drawable.ic_image))
                .into(imageView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.share, menu)
        menu.findItem(R.id.share).setOnMenuItemClickListener {
            val shareIntent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText(pageURL)
                    .intent
            if (shareIntent.resolveActivity(packageManager) != null) {
                startActivity(shareIntent)
            }
            true
        }

        return super.onCreateOptionsMenu(menu)
    }
}