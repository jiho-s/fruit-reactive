package me.jiho.fruitreactive.habits

data class HabitResult(val id: Long?, val name: String, val description: String, val isDone: Boolean? = null) {

    constructor(habit: Habit, isDone: Boolean? = null): this(habit.id, habit.name, habit.description, isDone)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HabitResult

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
