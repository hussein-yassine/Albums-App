package com.cme.songscompose.data.repositories

import com.cme.songscompose.data.network.AlbumsApi
import com.cme.songscompose.data.realm_models.RealmAlbum
import com.cme.songscompose.data.realm_models.RealmFeed
import com.cme.songscompose.utils.toRealmFeed
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsRepositoryImpl @Inject constructor(
    private val albumsApi: AlbumsApi,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val realm: Realm
) : AlbumsRepository {

    override suspend fun getAlbums(count: Int): Flow<Result<RealmFeed>> {
        /*return withContext(coroutineDispatcher) {
            try {
                Result.success(albumsApi.getTopSongs(count = count))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }*/
        return flow {
            val localFeed = getLocalFeed()
            if (localFeed?.albums?.isNotEmpty() == true) {
                Timber.tag("Repo").e("getAlbums: FROM DB")
                emit(Result.success(localFeed))
            }
            try {
                Timber.tag("Repo").e("getAlbums: FROM API")
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

    private fun getLocalAlbums(): List<RealmAlbum> {
        return realm.where<RealmAlbum>().findAll().map { realm.copyFromRealm(it) }
    }

    private fun saveFeedToLocal(feed: RealmFeed) {
        realm.executeTransactionAsync { transactionRealm ->
            transactionRealm.insertOrUpdate(feed)
        }
    }

}