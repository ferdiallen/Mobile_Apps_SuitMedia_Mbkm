package com.example.mobileappssuitmedia.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mobileappssuitmedia.local.LocalUserDatabase
import com.example.mobileappssuitmedia.local.LocalUserEntity
import com.example.mobileappssuitmedia.local.RemoteKeys
import com.example.mobileappssuitmedia.network.ApiService

@ExperimentalPagingApi
class LocalUserMediator(
    private val db: LocalUserDatabase,
    private val api: ApiService
) : RemoteMediator<Int, LocalUserEntity>() {

    override suspend fun initialize(): InitializeAction = InitializeAction.LAUNCH_INITIAL_REFRESH
    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, LocalUserEntity>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let {
            db.daoKeys().getRemoteKeys(it.id)
        }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, LocalUserEntity>): RemoteKeys? {
        return state.pages.lastOrNull() {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let {
            db.daoKeys().getRemoteKeys(it.id)
        }

    }

    private suspend fun getRemoteKeysClosestToCurrent(state: PagingState<Int, LocalUserEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                db.daoKeys().getRemoteKeys(it)
            }
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalUserEntity>
    ): MediatorResult {
        val firstPage = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeysClosestToCurrent(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeysForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                prevKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextKey
            }
        }
        val responsData = api.getAllRequest(firstPage, state.config.pageSize)
        val endofPagination = responsData.data.isEmpty()
        return try {
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.daoKeys().deleteAllRemoteKeys()
                    db.daoLocal().deleteAll()
                }
                val prevKey = if (firstPage == 1) null else firstPage - 1
                val nextKey = if (endofPagination) null else firstPage + 1
                val keys = responsData.data.map {
                    RemoteKeys(it.id, prevKey, nextKey)
                }
                db.daoKeys().insertData(keys)
                db.daoLocal().insertUser(responsData.toLocalDb())
            }
            MediatorResult.Success(endofPagination)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}