package com.example.beautiful_wallpaper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautiful_wallpaper.api.RetrofitInstance
import com.example.beautiful_wallpaper.api.SimpleApi
import com.example.beautiful_wallpaper.model.MyData
import com.example.beautiful_wallpaper.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Response<MyData>> = MutableLiveData()
    fun getData(){
        viewModelScope.launch {
            val response = repository.getData()
            myResponse.value = response
        }
    }
}