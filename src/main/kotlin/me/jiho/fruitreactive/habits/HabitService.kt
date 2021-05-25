package me.jiho.fruitreactive.habits

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

@Service
class HabitService(private val habitRepository: HabitRepository, private val habitExecutionDateRepository: HabitExecutionDateRepository) {

    @Transactional
    fun create(accountId: Long, name: String, description: String): Mono<Habit> =
        Mono.from(habitRepository.save(Habit(name= name, description = description, accountId = accountId)))

    @Transactional
    fun update(accountId: Long, habitId: Long, name: String?, description: String?): Mono<Habit> =
        Mono.from(habitRepository.findByIdAndAccountId(habitId, accountId)).flatMap { habit ->
            habitRepository.save(habit.copy(name = name ?: habit.name, description = description ?: habit.description))
        }

    @Transactional
    fun delete(accountId: Long, habitId: Long): Mono<Void> =
        Mono.from(habitRepository.findByIdAndAccountId(habitId, accountId)).flatMap { habit ->
            habitRepository.delete(habit)
        }

    @Transactional(readOnly = true)
    fun findAllHabitResult(accountId: Long, date: LocalDate): Flux<HabitResult> =
        Flux.from(habitRepository.findAllByAccountId(accountId)).collectList().zipWhen { habits ->
            habitExecutionDateRepository.findAllByHabitIdInAndDate(habits.map { it.id  }, date).collectMap { it.habitId ?: 0 }
        }.flatMapMany {
            Flux.fromIterable(it.t1).map { habit -> HabitResult(habit, it.t2.containsKey(habit.id)) }
        }

}

