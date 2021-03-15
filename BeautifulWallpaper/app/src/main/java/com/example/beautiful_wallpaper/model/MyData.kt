package com.example.beautiful_wallpaper.model

data class MyData(
    val photos: Photos,
    val stat: String
){
    fun getLinks() = photos.getLinks()
}