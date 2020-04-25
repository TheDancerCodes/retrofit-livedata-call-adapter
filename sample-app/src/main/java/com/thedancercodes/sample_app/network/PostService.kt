package com.thedancercodes.sample_app.network

import androidx.lifecycle.LiveData
import com.thedancercodes.livedatalibrary.ApiResponse
import com.thedancercodes.sample_app.model.Post
import retrofit2.http.GET

/**
 * Retrofit turns your HTTP API into a Kotlin interface
 *
 * NOTE: PostService methods use LiveData as their return type.
 */
interface PostService {

    @GET("posts")
    fun getPosts(): LiveData<ApiResponse<List<Post>>>
}