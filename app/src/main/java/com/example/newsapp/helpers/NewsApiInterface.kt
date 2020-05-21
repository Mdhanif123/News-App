package com.example.newsapp.helpers

import com.example.newsapp.dataModel.ArticleData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NewsApiInterface {
    @GET("top-headlines")
    fun getCountryNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Call<ArticleData>

    @GET("top-headlines")
    fun getAboutTrump(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String
    ): Call<ArticleData>

    @GET("top-headlines")
    fun getBBCNews(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String
    ): Call<ArticleData>

}