package com.suhel.photosphere.DataModels.Types.Derived

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.suhel.photosphere.DataModels.Types.BaseType
import com.suhel.photosphere.Singleton.*
import java.util.*

class User(var name: String = "",
           var onlineState: OnlineState = User.OnlineState.UNDEFINED,
           var loginState: Boolean = true,
           var currentAppVersion: Int = 0,
           var email: String = "",
           var profilePicUrl: String = "",
           var providerId: String = "",
           var providerType: ProviderType = ProviderType.UNDEFINED,
           var competitionIds: ArrayList<String> = ArrayList()) : BaseType(auth.currentUser?.uid ?: "") {

    enum class ProviderType {
        UNDEFINED,
        GOOGLE,
        FACEBOOK;

        var stringValue: String = "u"
            private set
            get() = when (this) {
                UNDEFINED -> "u"
                GOOGLE -> "g"
                FACEBOOK -> "f"
            }

        companion object {

            fun fromStringValue(stringValue: String?): ProviderType
                    = when (stringValue) {

                "u" -> UNDEFINED
                "g" -> GOOGLE
                "f" -> FACEBOOK
                else -> UNDEFINED

            }

        }
    }

    enum class OnlineState {
        ONLINE,
        OFFLINE,
        UNDEFINED;

        var stringValue: String = "undefined"
            private set
            get() = when (this) {
                ONLINE -> "online"
                OFFLINE -> "offline"
                UNDEFINED -> "undefined"
            }

        companion object {

            fun fromStringValue(stringValue: String?): OnlineState =
                    when (stringValue) {
                        "online" -> ONLINE
                        "offline" -> OFFLINE
                        "undefined" -> UNDEFINED
                        else -> UNDEFINED
                    }

        }
    }

    override fun insert() {
        if (this.id == "")
            return

        val map = HashMap<String, Any>()
        val insertEntryPoint = database.getReference(FIELD_USERS_ROOT).child(this.id)
        map[FIELD_USER_NAME] = this.name
        map[FIELD_USER_ONLINE_STATE] = this.onlineState.stringValue
        map[FIELD_USER_LOGIN_STATE] = this.loginState
        map[FIELD_USER_CURRENT_APP_VERSION] = this.currentAppVersion
        map[FIELD_USER_EMAIL] = this.email
        map[FIELD_USER_PROFILE_PIC_URL] = this.profilePicUrl
        map[FIELD_USER_PROVIDER_ID] = this.providerId
        map[FIELD_USER_PROVIDER_TYPE] = this.providerType.stringValue
        map[FIELD_USER_COMPETITION_IDS] = this.competitionIds
        insertEntryPoint.setValue(map)
    }

    override fun update() {
//        insert()
    }

    override fun remove() {
    }

    override fun <T : BaseType> copyFrom(data: T) {
        (data as? User)?.let {
            this.id = it.id
            this.name = it.name
            this.onlineState = it.onlineState
            this.loginState = it.loginState
            this.currentAppVersion = it.currentAppVersion
            this.email = it.email
            this.profilePicUrl = it.profilePicUrl
            this.providerId = it.providerId
            this.providerType = it.providerType
            this.competitionIds = it.competitionIds
        }
    }

    fun updateOnlineState(onlineState: OnlineState) {
        if (this.id == "")
            return

        this.onlineState = onlineState
        database.getReference(FIELD_USERS_ROOT).child(this.id).child(FIELD_USER_ONLINE_STATE)
                .setValue(onlineState.stringValue)
    }

    fun updateEmail(email: String) {
        if (this.id == "")
            return

        this.email = email
        database.getReference(FIELD_USERS_ROOT).child(this.id).child(FIELD_USER_EMAIL).setValue(email)
    }

    fun updateName(name: String) {
        if (this.id == "")
            return

        this.name = name
        database.getReference(FIELD_USERS_ROOT).child(this.id).child(FIELD_USER_NAME).setValue(name)
    }

    fun updateProviderId(providerId: String) {
        if (this.id == "")
            return

        this.providerId = providerId
        database.getReference(FIELD_USERS_ROOT).child(this.id).child(FIELD_USER_PROVIDER_ID).setValue(providerId)
    }

    fun updateProviderType(providerType: ProviderType) {
        if (this.id == "")
            return

        this.providerType = providerType
        database.getReference(FIELD_USERS_ROOT).child(this.id)
                .child(FIELD_USER_PROVIDER_TYPE).setValue(providerType.stringValue)
    }

    fun addCompetition(competitionId: String) {
        if (this.id != "" && !this.competitionIds.contains(competitionId)) {
            this.competitionIds.add(competitionId)
            database.getReference(FIELD_USERS_ROOT).child(this.id)
                    .child(FIELD_USER_COMPETITION_IDS).setValue(this.competitionIds)
        }
    }

    fun removeCompetition(competitionId: String) {
        if (this.id != "" && this.competitionIds.contains(competitionId)) {
            this.competitionIds.remove(competitionId)
            database.getReference(FIELD_USERS_ROOT).child(this.id)
                    .child(FIELD_USER_COMPETITION_IDS).setValue(this.competitionIds)
        }
    }

    companion object {

        fun fromDataSnapshot(dataSnapshot: DataSnapshot): User {
            val user = User()
            user.id = dataSnapshot.key
            user.name = dataSnapshot.child(FIELD_USER_NAME).getValue(String::class.java) ?: ""
            user.onlineState = OnlineState.fromStringValue(dataSnapshot.child(FIELD_USER_ONLINE_STATE)
                    .getValue(String::class.java))
            user.loginState = dataSnapshot.child(FIELD_USER_LOGIN_STATE).getValue(Boolean::class.java) ?: true
            user.currentAppVersion = dataSnapshot.child(FIELD_USER_CURRENT_APP_VERSION).getValue(Int::class.java) ?: 0
            user.email = dataSnapshot.child(FIELD_USER_EMAIL).getValue(String::class.java) ?: ""
            user.profilePicUrl = dataSnapshot.child(FIELD_USER_PROFILE_PIC_URL).getValue(String::class.java) ?: ""
            user.providerId = dataSnapshot.child(FIELD_USER_PROVIDER_ID).getValue(String::class.java) ?: ""
            user.providerType = ProviderType.fromStringValue(dataSnapshot.child(FIELD_USER_PROVIDER_TYPE)
                    .getValue(String::class.java))
            return user
        }

        fun changeCurrentUserOnlineState(onlineState: OnlineState) {
            auth.currentUser?.let {
                val userRef = database.getReference(FIELD_USERS_ROOT)
                        .child(it.uid).child(FIELD_USER_ONLINE_STATE)

                if (onlineState == OnlineState.ONLINE)
                    userRef.onDisconnect().setValue(OnlineState.OFFLINE.stringValue)

                userRef.setValue(onlineState.stringValue)
            }
        }

        fun changeLoginState(loginState: Boolean) {
            auth.currentUser?.let {
                val userRef = database.getReference(FIELD_USERS_ROOT)
                        .child(it.uid).child(FIELD_USER_LOGIN_STATE)

                userRef.setValue(loginState)
            }
        }

        fun changeCurrentAppVersion(currentAppVersion: Int) {
            auth.currentUser?.let {
                val userRef = database.getReference(FIELD_USERS_ROOT)
                        .child(it.uid).child(FIELD_USER_CURRENT_APP_VERSION)

                userRef.setValue(currentAppVersion)
            }
        }

    }

}

