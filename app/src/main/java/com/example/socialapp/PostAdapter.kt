package com.example.socialapp

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialapp.daos.PostDao
import com.example.socialapp.daos.UserDao
import com.example.socialapp.models.Post
import com.example.socialapp.models.User
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostAdapter(options: FirestoreRecyclerOptions<Post>) : FirestoreRecyclerAdapter<Post , PostViewHolder>(options)
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder =  PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.posts_rv_item , parent , false))
        viewHolder.likeIV.setOnClickListener {

        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.postContentTV.text = model.text
        holder.usernameTV.text = model.creator.displayName
        Glide.with(holder.userPhoto.context).load(model.creator.imageURL).circleCrop().into(holder.userPhoto)
        holder.likeCountTV.text = model.likedBy.size.toString()
        holder.createdAtTV.text = Utils.getTimeAgo(model.createdAtTime)

        val likeList = model.likedBy
        val currentUserId = FirebaseAuth.getInstance().uid

        if (likeList.contains(currentUserId)) {
            holder.likeIV.setImageResource(R.drawable.ic_baseline_favorite_24)
        }


        holder.likeIV.setOnClickListener {
            if (!likeList.contains(currentUserId)) {
                if (currentUserId != null) {
                    likeList.add(currentUserId)
                    model.likedBy = likeList
                    (it as ImageView).setImageResource(R.drawable.ic_baseline_favorite_24)
                    val postCollection  = PostDao().postCollection
                    val id = snapshots.getSnapshot(holder.absoluteAdapterPosition).id
                    postCollection.document(id).set(model)
                }
            }
        }
    }
}


class PostViewHolder(view : View) : RecyclerView.ViewHolder(view ) {
    val usernameTV : TextView = view.findViewById(R.id.tv_username)
    val userPhoto : ImageView = view.findViewById(R.id.iv_user_photo)
    val createdAtTV : TextView = view.findViewById(R.id.tv_created_at)
    val postContentTV : TextView = view.findViewById(R.id.tv_post_content)
    val likeIV : ImageView = view.findViewById(R.id.iv_like_button)
    val likeCountTV : TextView = view.findViewById(R.id.tv_like_count)
}