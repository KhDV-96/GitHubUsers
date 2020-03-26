package com.khdv.ghu.api

import com.khdv.ghu.model.UsersSearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com"

private val logger = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BASIC
}

private val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface GithubService {

    @GET("/search/users")
    fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") limit: Int
    ): Call<UsersSearchResponse>
}

object GithubApi {
    val retrofitService: GithubService by lazy {
        retrofit.create(GithubService::class.java)
    }
}