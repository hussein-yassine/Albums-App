package com.cme.songscompose.data.realm_models


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmGenre(
    @PrimaryKey var genreId: String = "",
    var name: String = "",
    var url: String = ""
): RealmObject()