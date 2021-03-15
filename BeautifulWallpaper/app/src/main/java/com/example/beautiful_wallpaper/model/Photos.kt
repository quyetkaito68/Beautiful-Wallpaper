package com.example.beautiful_wallpaper.model

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>,
    val total: Int
){
    fun getLinks(): List<String> {
        var list = ArrayList<String>()
        for (p in photo)
            list.add(p.getLink())
        return list
    }
}