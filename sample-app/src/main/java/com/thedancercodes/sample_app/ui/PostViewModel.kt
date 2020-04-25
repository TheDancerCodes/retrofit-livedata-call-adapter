package com.thedancercodes.sample_app.ui

import androidx.lifecycle.ViewModel
import com.thedancercodes.sample_app.network.PostService
import com.thedancercodes.sample_app.network.RetrofitClient

class PostViewModel() : ViewModel() {
    private val mainService: PostService =
        RetrofitClient().getRetrofit().create(PostService::class.java)
    val finalPosts = mainService.getPosts()
}