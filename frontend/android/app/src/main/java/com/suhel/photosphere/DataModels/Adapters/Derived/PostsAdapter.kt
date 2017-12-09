package com.suhel.photosphere.DataModels.Adapters.Derived

import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import com.suhel.photosphere.DataModels.Adapters.BaseAdapter
import com.suhel.photosphere.DataModels.Types.Derived.Post
import com.suhel.photosphere.Dialogs.PostDetailViewDialog
import com.suhel.photosphere.Helper.Utils
import com.suhel.photosphere.Views.ViewHolders.Derived.PostViewHolder


class PostsAdapter<out T : Query>(query: T, ascending: Boolean = false) :
        BaseAdapter<T, Post, PostViewHolder>(query, ascending) {

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val post = data[position]
        holder.bPostLike?.setOnClickListener {
            Utils.iff(data[position].isLikedByMe(), { post.removeMyLike() }, { post.addMyLike() })
        }
        holder.itemView?.setOnClickListener {
            PostDetailViewDialog.show(data, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PostViewHolder =
            PostViewHolder.fromParent(parent?.context, parent)

    override fun elementForDataSnapshot(dataSnapshot: DataSnapshot): Post = Post.fromDataSnapshot(dataSnapshot)

}