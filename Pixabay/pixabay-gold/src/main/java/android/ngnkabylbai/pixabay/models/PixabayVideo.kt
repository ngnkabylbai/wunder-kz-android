package android.ngnkabylbai.pixabay.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Nurbek Kabylbay on 24.03.2018.
 */
open class PixabayVideo ()  : RealmObject() {
    lateinit var tags: String
    lateinit var pictureId: String
    lateinit var videoURL: String
    lateinit var user: String
    @PrimaryKey
    lateinit var pageURL: String
    var views: Int = 0
    var likes: Int = 0
    var favorites: Int = 0

    constructor(tags: String, pictureId: String, videoURL: String, user: String, pageURL: String,
                views: Int, likes: Int, favorites: Int) : this() {
        this.tags = tags
        this.pictureId = pictureId
        this.videoURL = videoURL
        this.user = user
        this.pageURL = pageURL
        this.views = views
        this.likes = likes
        this.favorites = favorites
    }
}
