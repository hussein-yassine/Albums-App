package com.cme.songscompose.data.realm_models


import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmAlbum(
    @PrimaryKey var id: String = "",
    var artistName: String = "",
    var name: String = "",
    var releaseDate: String = "",
    var kind: String = "",
    var artistId: String? = "",
    var artistUrl: String? = "",
    var contentAdvisoryRating: String? = null,
    var artworkUrl100: String = "",
    var genres: RealmList<RealmGenre> = RealmList(),
    var url: String = ""
): RealmObject()