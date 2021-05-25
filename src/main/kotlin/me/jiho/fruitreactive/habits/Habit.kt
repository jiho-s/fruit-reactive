package me.jiho.fruitreactive.habits

import org.springframework.data.annotation.Id

data class Habit(@Id val id: Long? = null, val name: String, val description: String, val accountId: Long?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Habit

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
