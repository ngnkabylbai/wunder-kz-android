package android.ngnkabylbai.pixabay.adapter

import android.content.Context
import android.content.Intent
import android.ngnkabylbai.pixabay.models.PixabayImage
import android.ngnkabylbai.pixabay.R
import android.ngnkabylbai.pixabay.activity.ImageDetailActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

/**
 * Created by Nurbek Kabylbay on 23.03.2018.
 */
class PixabayImageAdapter(
        private val context: Context,
        private var images: ArrayList<PixabayImage>) : RecyclerView.Adapter<PixabayImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_pixabay, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = images[position]
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

    override fun getItemCount(): Int {
        return images.size
    }

    fun clear() {
        notifyItemRangeRemoved(0, images.size)
        images.clear()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val cardView: CardView = itemView.findViewById(R.id.itemView)
        val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
        val tagsTextView: TextView = itemView.findViewById(R.id.tagsTextView)
    }
}