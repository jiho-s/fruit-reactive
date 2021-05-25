package me.jiho.fruitreactive.habits

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

interface HabitExecutionDateRepository: R2dbcRepository<HabitExecutionDate, Long> {

    fun findByHabitIdAndDate(habitId: Long, date: LocalDate): Mono<HabitExecutionDate>

    fun findAllByHabitIdInAndDate(habitIds: List<Long?>, date: LocalDate): Flux<HabitExecutionDate>
}