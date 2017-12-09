package com.suhel.photosphere.DataModels.Types.Derived

import com.google.firebase.database.DataSnapshot
import com.suhel.photosphere.DataModels.Types.BaseType
import com.suhel.photosphere.Helper.refForPath
import com.suhel.photosphere.Singleton.FIELD_INFO_CRITICAL_VERSION
import com.suhel.photosphere.Singleton.FIELD_INFO_MESSAGE
import com.suhel.photosphere.Singleton.FIELD_INFO_VERSION

class Support(var version: Int = -1,
              var criticalVersion: Int = -1,
              var message: String = ""): BaseType() {

    override fun insert() {
        //
    }

    override fun update() {

    }

    override fun remove() {
    }

    override fun <T : BaseType> copyFrom(data: T) {
        (data as? Support)?.let {
            this.id = it.id
            this.version = it.version
            this.criticalVersion = it.criticalVersion
            this.message = it.message
        }
    }

    companion object {

        fun fromDataSnapshot(dataSnapshot: DataSnapshot): Support {
            val support = Support()
            support.id = dataSnapshot.key
            support.version = dataSnapshot.child(FIELD_INFO_VERSION).getValue(Int::class.java)!!
            support.criticalVersion = dataSnapshot.child(FIELD_INFO_CRITICAL_VERSION).getValue(Int::class.java)!!
            support.message = dataSnapshot.child(FIELD_INFO_MESSAGE).getValue(String::class.java)!!
            return support
        }

    }

}

