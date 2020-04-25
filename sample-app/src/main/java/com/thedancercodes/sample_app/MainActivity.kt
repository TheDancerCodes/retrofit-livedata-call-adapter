package com.thedancercodes.sample_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.thedancercodes.livedatalibrary.ApiResponse
import com.thedancercodes.sample_app.ui.PostListAdapter
import com.thedancercodes.sample_app.ui.PostViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG: String = "SampleAppDebug"

    private lateinit var postViewModel: PostViewModel // A member variable for the ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = PostListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get a ViewModel from the ViewModelProvider.
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        /**
         * Add an observer for the allPosts LiveData property from the WordViewModel.
         *
         * The onChanged() method (the default method for our Lambda) fires when the observed data
         * changes and the activity is in the foreground.
         */
        postViewModel.finalPosts.observe(this, Observer { response ->

            when(response) {
                is ApiResponse.ApiSuccessResponse -> {

                    // Update the cached copy of the posts in the adapter
                    adapter.setPosts(response.body)

                    Log.d(TAG, "POST RESPONSE: ${response.body}")
                }

                is ApiResponse.ApiErrorResponse -> {
                    Log.d(TAG, "POST ERROR: ${response.errorMessage}")

                    val snack = Snackbar
                        .make(recyclerview, response.errorMessage, Snackbar.LENGTH_LONG)
                    snack.show()
                }

                is ApiResponse.ApiEmptyResponse -> {
                    val snack = Snackbar
                        .make(recyclerview, "Empty Response", Snackbar.LENGTH_LONG)
                    snack.show()

                    Log.d(TAG, "POST EMPTY: Empty Response")
                }
            }
        })
    }
}