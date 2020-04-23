package com.thedancercodes.sample_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thedancercodes.sample_app.ui.PostListAdapter
import com.thedancercodes.sample_app.ui.PostViewModel

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private lateinit var wordViewModel: PostViewModel // A member variable for the ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = PostListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get a ViewModel from the ViewModelProvider.
        wordViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        /**
         * Add an observer for the allPosts LiveData property from the WordViewModel.
         *
         * The onChanged() method (the default method for our Lambda) fires when the observed data
         * changes and the activity is in the foreground.
         */
        wordViewModel.allPosts.observe(this, Observer { words ->
            // Update the cached copy of the posts in the adapter
            words?.let { adapter.setPosts(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * We want to open the NewWordActivity when tapping on the FAB and, once we are back in the
     * MainActivity, to either insert the new word in the database or show a Toast.
     */
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
//            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
//                val word = Word(it)
//                wordViewModel.insert(word)
//            }
//        } else {
//            Toast.makeText(
//                applicationContext,
//                R.string.empty_not_saved,
//                Toast.LENGTH_LONG).show()
//        }
//    }
}