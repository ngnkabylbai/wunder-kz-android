package android.ngnkabylbai.pixabay

import android.app.Application
import io.realm.Realm

/**
 * Created by Nurbek Kabylbay on 24.03.2018.
 */
class PixabayApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }
}