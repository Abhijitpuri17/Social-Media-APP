
package com.example.socialapp.daos

import android.util.Log
import android.widget.Toast
import com.example.socialapp.MainActivity
import com.example.socialapp.models.Post
import com.example.socialapp.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao
{
    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    val postCollection = db.collection("posts")

    fun addPost(text : String) {
        val currentUserId = mAuth.currentUser!!.uid
        GlobalScope.launch {
            val userDao = UserDao()
            val user = userDao.getUserFromID(currentUserId).await().toObject(User::class.java)
            val currentTime = System.currentTimeMillis()
            val post = Post(text , user!! , currentTime , ArrayList())
            postCollection.document().set(post)
            Log.d("done" , postCollection.toString())
        }
    }

}