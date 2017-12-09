package com.suhel.photosphere.DataModels.Types.Derived

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.storage.FirebaseStorage
import com.suhel.photosphere.DataModels.Types.BaseType
import com.suhel.photosphere.Helper.SimpleAnalytics
import com.suhel.photosphere.Singleton.*
import java.util.*

class Post(var title: String = "",
           var filePath: String = "",
           var fileURL: String = "",
           var date: Date = Calendar.getInstance().time,
           var likedBy: ArrayList<String> = ArrayList(),
           var competitionId: String = "global",
           var ownerId: String = auth.currentUser!!.uid) : BaseType() {

    override fun insert() {
        if (this.id != "")
            return

        val map = HashMap<String, Any>()
        val insertEntryPoint = database.getReference(FIELD_POSTS_ROOT).push()
        this.id = insertEntryPoint.key
        map[FIELD_POST_TITLE] = this.title
        map[FIELD_POST_FILE_PATH] = this.filePath
        map[FIELD_POST_FILE_URL] = this.fileURL
        map[FIELD_POST_DATE] = this.date
        map[FIELD_POST_LIKER_IDS] = this.likedBy
        map[FIELD_POST_COMPETITION_ID] = this.competitionId
        map[FIELD_POST_OWNER_ID] = this.ownerId
        insertEntryPoint.setValue(map)
    }

    override fun update() {
        if (this.id == "")
            return

        val map = HashMap<String, Any>()
        val updateEntryPoint = database.getReference(FIELD_POSTS_ROOT).child(this.id)
        map[FIELD_POST_TITLE] = this.title
        map[FIELD_POST_FILE_PATH] = this.filePath
        map[FIELD_POST_FILE_URL] = this.fileURL
        map[FIELD_POST_DATE] = this.date
        map[FIELD_POST_LIKER_IDS] = this.likedBy
        map[FIELD_POST_COMPETITION_ID] = this.competitionId
        map[FIELD_POST_OWNER_ID] = this.ownerId
        updateEntryPoint.setValue(map)
    }

    override fun remove() {
        if (this.id == "")
            return

        SimpleAnalytics.logEvent(SimpleAnalytics.EventType.ACTION, "Delete post " + id)
        storage.getReference(this.filePath).delete()
        database.getReference(FIELD_POSTS_ROOT).child(this.id).removeValue()
        this.id = ""
    }

    override fun <T : BaseType> copyFrom(data: T) {
        (data as? Post)?.let {
            this.id = it.id
            this.title = it.title
            this.filePath = it.filePath
            this.fileURL = it.fileURL
            this.date = it.date
            this.likedBy = it.likedBy
            this.competitionId = it.competitionId
            this.ownerId = it.ownerId
        }
    }

    fun updateTitle(title: String) {
        if (this.id == "")
            return

        SimpleAnalytics.logEvent(SimpleAnalytics.EventType.ACTION, "Update title " + id)
        this.title = title
        database.getReference(FIELD_POSTS_ROOT).child(this.id).child(FIELD_POST_TITLE).setValue(title)
    }

    fun updateFileURL(url: String) {
        if (this.id == "")
            return

        SimpleAnalytics.logEvent(SimpleAnalytics.EventType.ACTION, "Update fileurl " + id)
        this.fileURL = fileURL
        database.getReference(FIELD_POSTS_ROOT).child(this.id).child(FIELD_POST_FILE_URL).setValue(url)
    }

    private fun updateLikedBy() {
        if (this.id == "")
            return

        database.getReference(FIELD_POSTS_ROOT).child(this.id).child(FIELD_POST_LIKER_IDS).setValue(this.likedBy)
    }

    fun addLiker(liker: String) {
        if (!this.likedBy.contains(liker)) {
            this.likedBy.add(liker)

            //SimpleAnalytics.logEvent(SimpleAnalytics.EventType.ACTION, "Post $id like added by $liker")
            updateLikedBy()
        }
    }

    fun removeLiker(liker: String) {
        if (this.likedBy.contains(liker)) {
            this.likedBy.remove(liker)

            //SimpleAnalytics.logEvent(SimpleAnalytics.EventType.ACTION, "Post $id like removed by $liker")
            updateLikedBy()
        }
    }

    fun addMyLike() = addLiker(auth.currentUser!!.uid)

    fun removeMyLike() = removeLiker(auth.currentUser!!.uid)

    fun isLikedByMe(): Boolean = this.likedBy.contains(auth.currentUser!!.uid)

    fun isMyPost(): Boolean = (this.ownerId == auth.currentUser?.uid)

    companion object {

        fun fromDataSnapshot(dataSnapshot: DataSnapshot): Post {
            val post = Post()
            post.id = dataSnapshot.key
            post.title = dataSnapshot.child(FIELD_POST_TITLE).getValue(String::class.java) ?: ""
            post.filePath = dataSnapshot.child(FIELD_POST_FILE_PATH).getValue(String::class.java) ?: ""
            post.fileURL = dataSnapshot.child(FIELD_POST_FILE_URL).getValue(String::class.java) ?: ""
            post.date = dataSnapshot.child(FIELD_POST_DATE).getValue(Date::class.java) ?: Calendar.getInstance().time
            post.likedBy = dataSnapshot.child(FIELD_POST_LIKER_IDS)
                    .getValue(object : GenericTypeIndicator<ArrayList<String>>() {}) ?: ArrayList()
            post.competitionId = dataSnapshot.child(FIELD_POST_COMPETITION_ID)
                    .getValue(String::class.java) ?: ""
            post.ownerId = dataSnapshot.child(FIELD_POST_OWNER_ID).getValue(String::class.java) ?: ""
            return post
        }

    }

}