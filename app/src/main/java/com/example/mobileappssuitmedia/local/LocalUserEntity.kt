package com.example.mobileappssuitmedia.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalUserEntity(
    @PrimaryKey
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
)