package me.jiho.fruitreactive.habits

import org.springframework.data.annotation.Id
import java.time.LocalDate

data class HabitExecutionDate(@Id val id: Long? = null, val date: LocalDate, val habitId: Long?, val accountId: Long?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HabitExecutionDate

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}