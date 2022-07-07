package com.example.mobileappssuitmedia.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface LocalRemoteKeysDao {
    @Query("DELETE FROM RemoteKeys")
    suspend fun deleteAllRemoteKeys()

    @Insert(onConflict = REPLACE)
    suspend fun insertData(keys: List<RemoteKeys>)

    @Query("SELECT * FROM RemoteKeys WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): RemoteKeys?
}