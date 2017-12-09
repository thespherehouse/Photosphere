package com.suhel.photosphere.DataModels.Types.Derived

import com.google.firebase.database.*
import com.suhel.photosphere.DataModels.*
import com.suhel.photosphere.DataModels.Types.BaseType
import com.suhel.photosphere.Helper.generateFirebaseChild
import com.suhel.photosphere.Helper.generateFirebaseQuery
import com.suhel.photosphere.Singleton.*
import java.util.*

class Competition(var name: String = "",
                  var password: String = "",
                  var description: String = "",
                  var coverPath: String = "",
                  var hostId: String = "",
                  var participantIds: ArrayList<String> = ArrayList(),
                  var dateCreated: Date = calendar.time,
                  var dateStart: Date = calendar.time,
                  var dateEnd: Date = calendar.time) : BaseType() {

    override fun insert() {
        if (this.id != "")
            return

        val map = HashMap<String, Any>()
        val insertEntryPoint = FirebaseDatabase.getInstance().reference.child(FIELD_COMPETITIONS_ROOT).push()
        this.id = insertEntryPoint.key
        map[FIELD_COMPETITION_NAME] = this.name
        map[FIELD_COMPETITION_PASSWORD] = this.password
        map[FIELD_COMPETITION_DESCRIPTION] = this.description
        map[FIELD_COMPETITION_COVER_PATH] = this.coverPath
        map[FIELD_COMPETITION_HOST_ID] = this.hostId
        map[FIELD_COMPETITION_PARTICIPANT_IDS] = this.participantIds
        map[FIELD_COMPETITION_DATE_CREATED] = this.dateCreated
        map[FIELD_COMPETITION_DATE_START] = this.dateStart
        map[FIELD_COMPETITION_DATE_END] = this.dateEnd
        insertEntryPoint.setValue(map)

    }

    override fun update() {
        if (this.id == "")
            return

        val map = HashMap<String, Any>()
        val updateEntryPoint = FirebaseDatabase.getInstance().reference.child(FIELD_COMPETITIONS_ROOT).child(this.id)
        map[FIELD_COMPETITION_NAME] = this.name
        map[FIELD_COMPETITION_PASSWORD] = this.password
        map[FIELD_COMPETITION_DESCRIPTION] = this.description
        map[FIELD_COMPETITION_COVER_PATH] = this.coverPath
        map[FIELD_COMPETITION_HOST_ID] = this.hostId
        map[FIELD_COMPETITION_PARTICIPANT_IDS] = this.participantIds
        map[FIELD_COMPETITION_DATE_CREATED] = this.dateCreated
        map[FIELD_COMPETITION_DATE_START] = this.dateStart
        map[FIELD_COMPETITION_DATE_END] = this.dateEnd
        updateEntryPoint.setValue(map)

    }

    override fun remove() {
        if (this.id == "")
            return

        generateFirebaseQuery(FIELD_POSTS_ROOT, FIELD_POST_COMPETITION_ID, this.id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) {

                    }

                    override fun onDataChange(child: DataSnapshot?) {
                        child?.children?.forEach {
                            Post.fromDataSnapshot(it).remove()
                        }
                    }
                })
        generateFirebaseChild(FIELD_COMPETITIONS_ROOT)
                .child(this.id).removeValue()
        this.id = ""
    }

    override fun <T : BaseType> copyFrom(data: T) {
        (data as? Competition)?.let {
            this.id = it.id
            this.name = it.name
            this.password = it.password
            this.description = it.description
            this.coverPath = it.coverPath
            this.hostId = it.hostId
            this.participantIds = it.participantIds
            this.dateCreated = it.dateCreated
            this.dateStart = it.dateStart
            this.dateEnd = it.dateEnd
        }
    }


    fun isOngoing(): Boolean = Calendar.getInstance().time.let {
        return (it >= this.dateStart && it < this.dateEnd)
    }

    fun isEnrollable(): Boolean = Calendar.getInstance().time.let {
        return (it >= this.dateCreated && it < this.dateStart)
    }

    fun isEnded(): Boolean = Date().let {
        return it > this.dateEnd
    }

    fun isPasswordProtected(): Boolean = (this.password != "")

    fun isCoverPresent(): Boolean = (this.coverPath != "")

    fun checkPasswordValidity(password: String): Boolean = (this.password == password)

    fun updateName(name: String) {
        if (this.id != "") {

            this.name = name

            FirebaseDatabase.getInstance().reference.child(FIELD_COMPETITIONS_ROOT).child(this.id)
                    .child(FIELD_COMPETITION_NAME).setValue(name)

        }
    }

    fun updateDescription(description: String) {
        if (this.id != "") {

            this.description = description

            FirebaseDatabase.getInstance().reference.child(FIELD_COMPETITIONS_ROOT).child(this.id)
                    .child(FIELD_COMPETITION_DESCRIPTION).setValue(description)

        }
    }

    fun addParticipant(participantId: String) {
        if (this.id != "" && !this.participantIds.contains(participantId)) {
            this.participantIds.add(participantId)

            FirebaseDatabase.getInstance().reference.child(FIELD_COMPETITIONS_ROOT).child(this.id)
                    .child(FIELD_COMPETITION_PARTICIPANT_IDS).setValue(this.participantIds)
        }
    }

    fun removeParticipant(participantId: String) {
        if (this.id != "" && this.participantIds.contains(participantId)) {
            this.participantIds.remove(participantId)

            FirebaseDatabase.getInstance().reference.child(FIELD_COMPETITIONS_ROOT).child(this.id)
                    .child(FIELD_COMPETITION_PARTICIPANT_IDS).setValue(this.participantIds)
        }
    }

    companion object {

        fun fromDataSnapshot(dataSnapshot: DataSnapshot): Competition = Competition().let {
            it.id = dataSnapshot.key
            it.name = dataSnapshot.child(FIELD_COMPETITION_NAME).getValue(String::class.java) ?: ""
            it.password = dataSnapshot.child(FIELD_COMPETITION_PASSWORD).getValue(String::class.java) ?: ""
            it.description = dataSnapshot.child(FIELD_COMPETITION_DESCRIPTION).getValue(String::class.java) ?: ""
            it.coverPath = dataSnapshot.child(FIELD_COMPETITION_COVER_PATH).getValue(String::class.java) ?: ""
            it.hostId = dataSnapshot.child(FIELD_COMPETITION_HOST_ID).getValue(String::class.java) ?: ""
            it.participantIds = dataSnapshot.child(FIELD_COMPETITION_PARTICIPANT_IDS)
                    .getValue(object : GenericTypeIndicator<ArrayList<String>>() {}) ?: ArrayList()
            it.dateCreated = dataSnapshot.child(FIELD_COMPETITION_DATE_CREATED).getValue(Date::class.java) ?: calendar.time
            it.dateStart = dataSnapshot.child(FIELD_COMPETITION_DATE_START).getValue(Date::class.java) ?: calendar.time
            it.dateEnd = dataSnapshot.child(FIELD_COMPETITION_DATE_END).getValue(Date::class.java) ?: calendar.time
            return it
        }

    }

}