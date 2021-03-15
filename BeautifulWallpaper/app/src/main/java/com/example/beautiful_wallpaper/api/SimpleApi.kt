package com.example.beautiful_wallpaper.api

import com.example.beautiful_wallpaper.model.MyData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleApi {
    //https://www.flickr.com/services/rest/?method=flickr.photos.getPopular
    // &api_key=d0b35bb8f5b40cf47de1b2fef586c1a4&user_id=192231193%40N06&format=json&nojsoncallback=1
    @GET("services/rest")
    suspend fun getData(
        @Query("method") method: String,
        @Query("api_key") api_key: String,
        @Query("user_id") user_id: String,
        @Query("format") format: String,
        @Query("nojsoncallback") nojsoncallback: Int
    ): Response<MyData>

    @GET("services/rest")
    fun getData2(
        @Query("method") method: String,
        @Query("api_key") api_key: String,
        @Query("user_id") user_id: String,
        @Query("format") format: String,
        @Query("nojsoncallback") nojsoncallback: Int
    ): Call<MyData>
}