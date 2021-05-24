package me.jiho.fruitreactive.commons

import org.springframework.http.HttpStatus

data class ApiError(val message: String, val status: Int) {
    constructor(message: String, status: HttpStatus): this(message, status.value())
}
