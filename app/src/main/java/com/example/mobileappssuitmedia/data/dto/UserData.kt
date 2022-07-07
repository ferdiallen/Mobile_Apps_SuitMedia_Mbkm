package com.example.mobileappssuitmedia.data.dto

import com.example.mobileappssuitmedia.local.LocalUserEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageData(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<UserData>,
    val support: SupportData
) {
    fun toLocalDb(): List<LocalUserEntity> {
        return data.map { out ->
            LocalUserEntity(
                out.id, out.email, out.firstName, out.lastName, out.avatar
            )
        }
    }
}

@Serializable
data class UserData(
    val id: Int,
    val email: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val avatar: String
)

@Serializable
data class SupportData(
    val url: String,
    val text: String
)
