package labs.startup.nurbekkabylbai.kitapp

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

/**
 * Created by Nurbek Kabylbay on 04.03.2018.
 */
class BooksAdapter(context: Context): BaseAdapter() {

    private var books: JSONArray = JSONArray()

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val requestQueue = Volley.newRequestQueue(context)
    private val imageLoader = ImageLoader(requestQueue, object: ImageLoader.ImageCache {

        private val cache = LruCache<String, Bitmap>(20)

        override fun getBitmap(url: String?): Bitmap? {
            return cache.get(url)
        }

        override fun putBitmap(url: String?, bitmap: Bitmap?) {
            cache.put(url, bitmap)
        }
    })

    fun loadNewBooks(newBooks: JSONArray) {
        for(i in 0 until newBooks.length()) {
            books.put(newBooks.getJSONObject(i))
        }
        Log.d("TEST", "Books loaded")
        notifyDataSetChanged()
    }

    fun clear() {
        books = JSONArray()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        lateinit var viewHolder: ViewHolder
        var view: View? = null

        if(convertView == null) {
            view = inflater.inflate(R.layout.row_item, null)

            viewHolder = ViewHolder()
            viewHolder.titleTextView = view.findViewById(R.id.title)
            viewHolder.subtitleTextView = view.findViewById(R.id.subtitle)
            viewHolder.pageCountTextView = view.findViewById(R.id.pages)
            viewHolder.coverImageView = view.findViewById(R.id.coverImageView)

            view.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        try {
            val book = books.getJSONObject(position)
            val volumeInfo = book.getJSONObject("volumeInfo")

            val title = volumeInfo.getString("title")
            val subtitle = volumeInfo.getString("subtitle")
            val pageCount = "Pages: ${volumeInfo.getInt("pageCount").toString()}"

            viewHolder.titleTextView.text = title
            viewHolder.subtitleTextView.text = subtitle
            viewHolder.pageCountTextView.text = pageCount

            val imageLinks = volumeInfo.getJSONObject("imageLinks")
            val thumbnailLink = imageLinks.getString("thumbnail")

            if(thumbnailLink != null)
                viewHolder.coverImageView.setImageUrl(thumbnailLink, imageLoader)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return view ?: convertView!!
    }

    override fun getItem(position: Int): Any {
        return books[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return books.length()
    }

    private inner class ViewHolder {
        lateinit var titleTextView: TextView
        lateinit var subtitleTextView: TextView
        lateinit var pageCountTextView: TextView
        lateinit var coverImageView: NetworkImageView
    }
}