package me.jiho.fruitreactive.habits

import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface HabitRepository: R2dbcRepository<Habit, Long> {

    fun findAllByAccountId(accountId: Long): Flux<Habit>

    fun findByIdAndAccountId(habitId: Long, accountId: Long): Mono<Habit>
}