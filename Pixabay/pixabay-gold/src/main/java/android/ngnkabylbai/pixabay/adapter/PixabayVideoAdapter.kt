package android.ngnkabylbai.pixabay.adapter

import android.content.Context
import android.content.Intent
import android.ngnkabylbai.pixabay.R
import android.ngnkabylbai.pixabay.activity.ImageDetailActivity
import android.ngnkabylbai.pixabay.activity.VideoDetailActivity
import android.ngnkabylbai.pixabay.models.PixabayVideo
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

/**
 * Created by Nurbek Kabylbay on 24.03.2018.
 */
class PixabayVideoAdapter(
        private val context: Context,
        private val videos: ArrayList<PixabayVideo>) : RecyclerView.Adapter<PixabayVideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_pixabay, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = videos[position]

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

    override fun getItemCount(): Int {
        return videos.size
    }

    fun clear() {
        notifyItemRangeRemoved(0, videos.size)
        videos.clear()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val cardView: CardView = itemView.findViewById(R.id.itemView)
        val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
        val tagsTextView: TextView = itemView.findViewById(R.id.tagsTextView)
    }
}