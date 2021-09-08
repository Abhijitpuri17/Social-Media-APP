package com.example.socialapp.daos

import com.example.socialapp.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserDao {
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("Users")

    fun addUser(user: User?) {
        user?.let {
            usersCollection.document(user.uid).set(it)
        }
    }

    fun getUserFromID(uid: String): Task<DocumentSnapshot> {
        return usersCollection.document(uid).get()
    }


}