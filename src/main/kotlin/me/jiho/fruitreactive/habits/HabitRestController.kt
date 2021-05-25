package me.jiho.fruitreactive.habits

import me.jiho.fruitreactive.commons.ApiResult
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.time.LocalDate

@RestController
@RequestMapping("/api/habit")
class HabitRestController(private val habitService: HabitService) {

    private val id = 1L

    @GetMapping("/list")
    fun getList(): Mono<ApiResult<List<HabitResult>>> =
        habitService.findAllHabitResult(id, LocalDate.now()).collectList().map(ApiResult.Companion::success)

    @PutMapping("/{habitId}")
    fun update(@PathVariable habitId: Long, @RequestBody habitRequest: HabitRequest): Mono<ApiResult<HabitResult>> =
        habitService.update(id, habitId, habitRequest.name, habitRequest.description)
            .map(::HabitResult).map(ApiResult.Companion::success)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun create(@RequestBody habitRequest: HabitRequest): Mono<ApiResult<HabitResult>> =
        habitService.create(id,
            habitRequest.name ?: throw IllegalArgumentException("name must be provided"),
            habitRequest.description).map(::HabitResult).map(ApiResult.Companion::success)

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{habitId}")
    fun delete(@PathVariable habitId: Long): Mono<ApiResult<Void>> =
        habitService.delete(id, habitId).map(ApiResult.Companion::success)

    @PatchMapping("/do/{habitId}")
    fun doHabit(@PathVariable habitId: Long): Mono<ApiResult<HabitResult>> =
        habitService.doHabit(id, habitId, LocalDate.now()).map(ApiResult.Companion::success)

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/undo/{habitId}")
    fun undoHabit(@PathVariable habitId: Long): Mono<ApiResult<Void>> =
        habitService.undoHabit(id, habitId, LocalDate.now()).map(ApiResult.Companion::success)
}