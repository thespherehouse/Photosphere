package com.suhel.photosphere.DataModels.Types

abstract class BaseType(var id: String = "") {

    abstract fun insert()

    abstract fun update()

    abstract fun remove()

    abstract fun<T: BaseType> copyFrom(data: T)

    override fun equals(other: Any?): Boolean {
        return this.id == (other as? BaseType)?.id
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

}