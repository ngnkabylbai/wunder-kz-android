package android.ngnkabylbai.pixabay.activity

import android.net.Uri
import android.ngnkabylbai.pixabay.R
import android.ngnkabylbai.pixabay.models.PixabayImage
import android.ngnkabylbai.pixabay.models.PixabayVideo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_video_detail.*
import android.support.v4.app.ShareCompat
import android.widget.Toast
import io.realm.Realm


/**
 * Created by Nurbek Kabylbay on 24.03.2018.
 */
class VideoDetailActivity : AppCompatActivity() {

    private lateinit var tags: String
    private lateinit var pictureId: String
    private lateinit var videoURL: String
    private lateinit var user: String
    private lateinit var pageURL: String
    private var views = 0
    private var likes = 0
    private var favorites = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)

        tags = intent.getStringExtra("tags")
        pictureId = intent.getStringExtra("pictureId")
        videoURL = intent.getStringExtra("videoURL")
        user = intent.getStringExtra("user")
        pageURL = intent.getStringExtra("pageURL")
        views = intent.getIntExtra("views", 0)
        likes = intent.getIntExtra("likes", 0)
        favorites = intent.getIntExtra("favorites", 0)

        tagsTextView.text = tags
        authorTextView.text = user
        viewCountTextView.text = views.toString()
        likeCountTextView.text = likes.toString()
        favCountTextView.text = favorites.toString()

        videoView.setVideoURI(Uri.parse(videoURL))

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail, menu)
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

        menu.findItem(R.id.makeFavorite).setOnMenuItemClickListener {
            Realm.getDefaultInstance().executeTransaction { realm ->
                realm.insertOrUpdate(PixabayVideo(tags, pictureId, videoURL, user, pageURL,
                        views, likes, favorites))

                Toast.makeText(this, "Добавлено в избранные", Toast.LENGTH_SHORT).show()
            }
            true
        }

        return super.onCreateOptionsMenu(menu)
    }
}