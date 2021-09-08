package com.example.socialapp.models

data class Post (
    val text : String = "",
    val creator : User = User(),
    val createdAtTime : Long = 0L,
    var likedBy: ArrayList<String> = ArrayList()
        )