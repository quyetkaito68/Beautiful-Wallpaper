package com.example.beautiful_wallpaper.model

data class Photo(
    val farm: Int,
    val id: String,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String
){
    fun getLink() = "https://live.staticflickr.com/" + server + "/" + id + "_" + secret + "_b.jpg"
}

