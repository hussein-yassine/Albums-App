package com.cme.songscompose.data.realm_models


import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmFeed(
    @PrimaryKey var id: String = "",
    var title: String = "",
    var copyright: String = "",
    var country: String = "",
    var icon: String = "",
    var updated: String = "",
    var albums: RealmList<RealmAlbum> = RealmList()
): RealmObject()