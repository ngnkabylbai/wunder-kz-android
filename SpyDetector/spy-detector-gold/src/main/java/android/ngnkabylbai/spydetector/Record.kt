package android.ngnkabylbai.spydetector

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Nurbek Kabylbay on 14.03.2018.
 */
open class Record : RealmObject() {

    @PrimaryKey
    var name = "Unknown"
    var score = 0

}
