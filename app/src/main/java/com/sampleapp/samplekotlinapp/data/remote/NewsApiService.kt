package com.sampleapp.samplekotlinapp.data.remote
import com.sampleapp.samplekotlinapp.data.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService{
    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey : String
    ): Response<NewsResponse>
}