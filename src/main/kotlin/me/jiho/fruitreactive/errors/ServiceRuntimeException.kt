package me.jiho.fruitreactive.errors

abstract class ServiceRuntimeException(val messageKey: String,
                                       val detailKey: String,
                                       val params: Array<Any>): RuntimeException()