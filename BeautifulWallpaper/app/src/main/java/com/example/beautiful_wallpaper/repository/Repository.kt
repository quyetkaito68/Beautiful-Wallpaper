package com.example.beautiful_wallpaper.repository

import com.example.beautiful_wallpaper.api.RetrofitInstance
import com.example.beautiful_wallpaper.model.MyData
import com.example.beautiful_wallpaper.util.Constants.Companion.API_KEY
import com.example.beautiful_wallpaper.util.Constants.Companion.METHOD
import com.example.beautiful_wallpaper.util.Constants.Companion.USER_ID
import retrofit2.Call
import retrofit2.Response

class Repository {
    suspend fun getData(): Response<MyData> {
        return RetrofitInstance.api.getData(METHOD, API_KEY, USER_ID,"json",1)
    }
}