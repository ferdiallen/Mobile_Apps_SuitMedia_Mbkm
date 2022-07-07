package com.example.mobileappssuitmedia.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mobileappssuitmedia.local.LocalUserDatabase
import com.example.mobileappssuitmedia.local.LocalUserEntity
import com.example.mobileappssuitmedia.network.ApiService
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class MediatorRepository(private val db: LocalUserDatabase, private val api: ApiService)  {
    fun retrieveData(): Flow<PagingData<LocalUserEntity>>{
        return Pager(
            config = PagingConfig(
                3
            ), remoteMediator = LocalUserMediator(db, api),
            pagingSourceFactory = {
                db.daoLocal().getALlData()
            }
        ).flow
    }
}