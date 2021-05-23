package me.jiho.fruitreactive.errors

import me.jiho.fruitreactive.accounts.Account
import kotlin.reflect.KClass

class DuplicateException(targetName: String, vararg values: Any)
    : ServiceRuntimeException(messageKey = MESSAGE_KEY, detailKey = MESSAGE_DETAILS, arrayOf(targetName, values.joinToString(","))) {
    companion object {
        const val MESSAGE_KEY = "error.duplicate"
        const val MESSAGE_DETAILS = "error.duplicate.details"
    }
    constructor(klass: KClass<out Any>, vararg values: Any) : this(klass.simpleName ?: "Klass", values)
}