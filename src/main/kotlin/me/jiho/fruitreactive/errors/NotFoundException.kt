package me.jiho.fruitreactive.errors

import kotlin.reflect.KClass

class NotFoundException(targetName: String, vararg values: Any)
    : ServiceRuntimeException(messageKey = MESSAGE_KEY, detailKey = MESSAGE_DETAILS, arrayOf(targetName, values.joinToString(","))) {
    companion object {
        const val MESSAGE_KEY = "error.notfound"
        const val MESSAGE_DETAILS = "error.notfound.details"
    }
    constructor(klass: KClass<Any>, vararg values: Any) : this(klass.simpleName ?: "Klass", values)
}