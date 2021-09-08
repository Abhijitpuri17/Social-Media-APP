package com.example.socialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socialapp.daos.PostDao
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {

    lateinit var postDao : PostDao

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        postDao = PostDao()

        btn_post_submit.setOnClickListener{
                      postDao.addPost(et_post.text.toString())
                      finish()
          }


    }
}