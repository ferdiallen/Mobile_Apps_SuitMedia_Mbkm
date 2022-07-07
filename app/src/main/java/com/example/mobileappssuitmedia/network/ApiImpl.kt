package com.example.mobileappssuitmedia.network

import android.util.Log
import com.example.mobileappssuitmedia.data.dto.PageData
import com.example.mobileappssuitmedia.data.dto.SupportData
import com.example.mobileappssuitmedia.utils.BaseUrl
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import java.io.IOException
import kotlin.Exception

class ApiImpl(
    private val client: HttpClient
) : ApiService {
    override suspend fun getAllRequest(page: Int, size: Int): PageData {
        return try {
            client.get {
                url("${BaseUrl.BASE_URL}api/users")
                parameter("page", page)
                parameter("per_page", size)
            }.body()
        } catch (e: Exception) {

            PageData(0, 0, 0, 0, emptyList(), SupportData("", ""))
        } catch (e: IOException) {

            PageData(0, 0, 0, 0, emptyList(), SupportData("", ""))
        }
    }
}