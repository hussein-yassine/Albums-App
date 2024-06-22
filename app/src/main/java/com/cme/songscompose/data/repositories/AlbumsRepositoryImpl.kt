package com.cme.songscompose.data.repositories

import com.cme.songscompose.data.network.AlbumsApi
import com.cme.songscompose.data.realm_models.RealmAlbum
import com.cme.songscompose.data.realm_models.RealmFeed
import com.cme.songscompose.utils.fastLog
import com.cme.songscompose.utils.toRealmFeed
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsRepositoryImpl @Inject constructor(
    private val albumsApi: AlbumsApi,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val realm: Realm
) : AlbumsRepository {

    override suspend fun getAlbums(count: Int): Flow<Result<RealmFeed>> {
        return flow {
            val localFeed = getLocalFeed()
            if (localFeed?.albums?.isNotEmpty() == true) {
                fastLog("Repo getAlbums: FROM DB")
                emit(Result.success(localFeed))
            }
            try {
                fastLog("Repo getAlbums: FROM API")
                val response = withContext(coroutineDispatcher) {
                    albumsApi.getTopSongs(count = count)
                }
                val realmFeed = response.feed.toRealmFeed()
                saveFeedToLocal(realmFeed)
                emit(Result.success(realmFeed))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }

    private fun getLocalFeed(): RealmFeed? {
        return realm.where<RealmFeed>().findFirst()?.let { realm.copyFromRealm(it) }
    }

    private fun saveFeedToLocal(feed: RealmFeed) {
        realm.executeTransactionAsync { transactionRealm ->
            transactionRealm.insertOrUpdate(feed)
        }
    }

    override suspend fun getAlbumById(albumId: String): Result<RealmAlbum> {
        return withContext(coroutineDispatcher) {
            val realm = Realm.getDefaultInstance()
            try {
                val album = realm.where(RealmAlbum::class.java).equalTo("id", albumId).findFirst()
                if (album != null) {
                    Result.success(realm.copyFromRealm(album))
                } else {
                    Result.failure(Exception("Album not found"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            } finally {
                realm.close()
            }
        }
    }
}