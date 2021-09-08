package com.example.socialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialapp.daos.PostDao
import com.example.socialapp.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : PostAdapter
    private lateinit var postDao : PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_add_post.setOnClickListener {
            val intent = Intent(this , CreatePostActivity::class.java)
            startActivity(intent)
        }
        postDao = PostDao()


        setupRecylerView()

    }

    private fun setupRecylerView() {

        val postsCollection = postDao.postCollection
        val query = postsCollection.orderBy("createdAtTime" , Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query , Post::class.java).build()

        adapter = PostAdapter(recyclerViewOptions)
        posts_recycler_view.layoutManager = LinearLayoutManager(this)
        posts_recycler_view.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


}