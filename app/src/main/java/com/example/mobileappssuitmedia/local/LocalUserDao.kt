package com.example.mobileappssuitmedia.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface LocalUserDao {

    @Query("SELECT * FROM LocalUserEntity")
    fun getALlData(): PagingSource<Int, LocalUserEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertUser(user: List<LocalUserEntity>)

    @Query("DELETE FROM LocalUserEntity")
    suspend fun deleteAll()
}