package com.example.mobileappssuitmedia.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalUserEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class LocalUserDatabase : RoomDatabase() {
    abstract fun daoLocal(): LocalUserDao
    abstract fun daoKeys(): LocalRemoteKeysDao
}