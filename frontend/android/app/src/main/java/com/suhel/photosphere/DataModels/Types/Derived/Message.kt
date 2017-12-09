package com.suhel.photosphere.DataModels.Types.Derived

import com.google.firebase.database.ServerValue
import com.suhel.photosphere.DataModels.Types.BaseType
import com.suhel.photosphere.Singleton.*
import java.util.HashMap

class Message(var text: String = "",
              var conversationId: String = "",
              var time: Map<String, String> = ServerValue.TIMESTAMP,
              var fromId: String = ""): BaseType() {

    override fun insert() {
        if (this.id != "")
            return

        val map = HashMap<String, Any>()
        val insertEntryPoint = database.getReference(FIELD_MESSAGES_ROOT).push()
        this.id = insertEntryPoint.key
        map[FIELD_MESSAGE_TEXT] = this.text
        map[FIELD_MESSAGE_CONVERSATION_ID] = this.conversationId
        map[FIELD_MESSAGE_TIME] = this.time
        map[FIELD_MESSAGE_FROM_ID] = this.fromId
        insertEntryPoint.setValue(map)
    }

    override fun update() {
    }

    override fun remove() {
        if(this.id == "")
            return

        database.getReference(FIELD_MESSAGES_ROOT).child(this.id).removeValue()
        this.id = ""
    }

    override fun <T : BaseType> copyFrom(data: T) {
    }


}
