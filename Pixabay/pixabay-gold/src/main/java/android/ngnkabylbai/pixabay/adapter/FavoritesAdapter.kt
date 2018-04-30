package android.ngnkabylbai.pixabay.adapter

import android.content.Context
import android.content.Intent
import android.ngnkabylbai.pixabay.R
import android.ngnkabylbai.pixabay.activity.ImageDetailActivity
import android.ngnkabylbai.pixabay.activity.VideoDetailActivity
import android.ngnkabylbai.pixabay.models.PixabayImage
import android.ngnkabylbai.pixabay.models.PixabayVideo
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import io.realm.RealmObject

/**
 * Created by Nurbek Kabylbay on 24.03.2018.
 */
class FavoritesAdapter(
        private val context: Context,
        private val items: ArrayList<RealmObject>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val IMAGE_VIEW_TYPE = 0
    private val VIDEO_VIEW_TYPE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =LayoutInflater.from(context).inflate(R.layout.item_pixabay, parent, false)
        return when(viewType) {
            IMAGE_VIEW_TYPE -> PixabayImageAdapter.ViewHolder(itemView)
            else -> PixabayVideoAdapter.ViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is PixabayImageAdapter.ViewHolder -> {
                val item = items[position] as PixabayImage
                Glide.with(context).load(item.webformatURL).into(holder.imageView)

                holder.authorTextView.text = item.user
                holder.tagsTextView.text = item.tags
                holder.imageView.setOnClickListener {
                    val intent = Intent(context, ImageDetailActivity::class.java)
                    intent.putExtra("tags", item.tags)
                    intent.putExtra("webformatURL", item.webformatURL)
                    intent.putExtra("favorites", item.favorites)
                    intent.putExtra("pageURL", item.pageURL)
                    intent.putExtra("likes", item.likes)
                    intent.putExtra("comments", item.comments)
                    intent.putExtra("user", item.user)

                    context.startActivity(intent)
                }
            }
            is PixabayVideoAdapter.ViewHolder -> {
                val item = items[position] as PixabayVideo

                Glide.with(context).load("https://i.vimeocdn.com/video/${item.pictureId}_295x166.jpg").into(holder.imageView)
                holder.authorTextView.text = item.user
                holder.tagsTextView.text = item.tags
                holder.imageView.setOnClickListener {
                    val intent = Intent(context, VideoDetailActivity::class.java)
                    intent.putExtra("tags", item.tags)
                    intent.putExtra("pictureId", item.pictureId)
                    intent.putExtra("videoURL", item.videoURL)
                    intent.putExtra("user", item.user)
                    intent.putExtra("pageURL", item.pageURL)
                    intent.putExtra("views", item.views)
                    intent.putExtra("likes", item.likes)
                    intent.putExtra("favorites", item.favorites)

                    context.startActivity(intent)
                }
            }
            else -> { }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return when (item) {
            is PixabayImage -> IMAGE_VIEW_TYPE
            is PixabayVideo -> VIDEO_VIEW_TYPE
            else -> -1
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}