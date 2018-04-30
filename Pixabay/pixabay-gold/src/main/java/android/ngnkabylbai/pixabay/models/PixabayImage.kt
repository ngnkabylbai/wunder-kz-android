package android.ngnkabylbai.pixabay.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Nurbek Kabylbay on 23.03.2018.
 */
open class PixabayImage()  : RealmObject() {
    lateinit var tags: String
    lateinit var webformatURL: String
    lateinit var user: String
    @PrimaryKey
    lateinit var pageURL: String
    var favorites: Int = 0
    var likes: Int = 0
    var comments: Int = 0

    constructor(tags: String, webformatURL: String, user: String, pageURL: String, favorites: Int,
            likes: Int, comments: Int) : this() {
        this.tags = tags
        this.webformatURL = webformatURL
        this.user = user
        this.pageURL = pageURL
        this.favorites = favorites
        this.likes = likes
        this.comments = comments
    }
}
