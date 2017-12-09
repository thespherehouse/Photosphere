package com.suhel.photosphere.Helper

class Utils {

    companion object {

        fun iff(expression: Boolean, isTrue: (() -> Unit)?, isFalse: (() -> Unit)? = null) =
                if(expression) isTrue?.invoke() else isFalse?.invoke()

    }

}
