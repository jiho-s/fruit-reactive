package me.jiho.fruitreactive.accounts

import org.springframework.data.annotation.Id

data class Account(
    @Id val id: Long? = null,
    val email: String,
    val password: String,
    val appleInTree: Int = 0,
    val appleInBasket: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
