package com.example.mobileappssuitmedia.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(
    @PrimaryKey
    val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
