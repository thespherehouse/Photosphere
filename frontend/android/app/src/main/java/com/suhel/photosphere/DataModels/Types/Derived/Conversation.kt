package com.suhel.photosphere.DataModels.Types.Derived

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.GenericTypeIndicator
import com.suhel.photosphere.DataModels.Types.BaseType
import com.suhel.photosphere.Singleton.FIELD_CONVERSATIONS_ROOT
import com.suhel.photosphere.Singleton.FIELD_CONVERSATION_TITLE
import com.suhel.photosphere.Singleton.FIELD_CONVERSATION_USER_IDS
import com.suhel.photosphere.Singleton.database

class Conversation(var title: String = "",
                   var userIds: HashMap<String, Boolean> = HashMap()): BaseType() {

    override fun insert() {
        if(this.id != "")
            return

        val map = HashMap<String, Any>()
        val insertEntryPoint = database.getReference(FIELD_CONVERSATIONS_ROOT).push()
        this.id = insertEntryPoint.key
        map[FIELD_CONVERSATION_TITLE] = this.title
        map[FIELD_CONVERSATION_USER_IDS] = this.userIds
        insertEntryPoint.setValue(map)
    }

    override fun update() {

    }

    override fun remove() {
    }

    fun updateTitle(title: String) {

    }

    override fun <T : BaseType> copyFrom(data: T) {
    }

    companion object {

        fun fromDataSnapshot(dataSnapshot: DataSnapshot): Conversation {
            val conversation = Conversation()
            conversation.id = dataSnapshot.key
            conversation.title = dataSnapshot.child(FIELD_CONVERSATION_TITLE).getValue(String::class.java) ?: ""
            conversation.userIds = dataSnapshot.child(FIELD_CONVERSATION_USER_IDS).getValue(object : GenericTypeIndicator<HashMap<String, Boolean>>() {}) ?: HashMap()
            return conversation
        }

    }

}