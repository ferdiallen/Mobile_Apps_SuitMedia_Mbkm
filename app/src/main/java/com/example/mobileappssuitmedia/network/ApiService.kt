package com.example.mobileappssuitmedia.network

import com.example.mobileappssuitmedia.data.dto.PageData

interface ApiService {
    suspend fun getAllRequest(page: Int, size: Int): PageData
}