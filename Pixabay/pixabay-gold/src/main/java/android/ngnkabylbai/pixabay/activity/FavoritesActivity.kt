package android.ngnkabylbai.pixabay.activity

import android.ngnkabylbai.pixabay.R
import android.ngnkabylbai.pixabay.adapter.FavoritesAdapter
import android.ngnkabylbai.pixabay.adapter.PixabayImageAdapter
import android.ngnkabylbai.pixabay.models.PixabayImage
import android.ngnkabylbai.pixabay.models.PixabayVideo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Nurbek Kabylbay on 24.03.2018.
 */
class FavoritesActivity: AppCompatActivity() {

    private lateinit var adapter: FavoritesAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager

        adapter = FavoritesAdapter(this, getLoadedObjects())
        recyclerView.adapter = adapter
    }

    private fun getLoadedObjects(): ArrayList<RealmObject> {
        val items = ArrayList<RealmObject>()
        items += Realm.getDefaultInstance().where(PixabayImage::class.java).findAll()
        items +=Realm.getDefaultInstance().where(PixabayVideo::class.java).findAll()
        return items
    }
}