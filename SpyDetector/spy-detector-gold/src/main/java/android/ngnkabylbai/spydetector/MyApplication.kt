package android.ngnkabylbai.spydetector

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by Nurbek Kabylbay on 14.03.2018.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val config = RealmConfiguration.Builder().name("spy-detector.realm").build()
        Realm.setDefaultConfiguration(config)
    }
}
