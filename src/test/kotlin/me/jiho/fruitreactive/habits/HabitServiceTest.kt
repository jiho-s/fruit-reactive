package me.jiho.fruitreactive.habits

import me.jiho.fruitreactive.accounts.Account
import me.jiho.fruitreactive.accounts.AccountRepository
import me.jiho.fruitreactive.errors.NotFoundException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import reactor.test.StepVerifier
import java.time.LocalDate

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class HabitServiceTest {

    @Autowired
    private lateinit var habitService: HabitService

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    private lateinit var habitRepository: HabitRepository

    @Autowired
    private lateinit var habitExecutionDateRepository: HabitExecutionDateRepository

    lateinit var habit: Habit

    lateinit var account: Account

    @BeforeAll
    fun setUp() {
        account = Account(email = "test@email.com", password = "pass")
        accountRepository.save(account).subscribe { account = it }

        habitRepository.save(Habit(name = "test0", description = "test0 description", accountId = account.id)).subscribe {
            habit = it
        }
        Flux.range(1,3).flatMap {
            habitRepository.save(Habit(name = "test$it", description = "test$it description", accountId = account.id))
        }.flatMap {
            habitExecutionDateRepository.save(HabitExecutionDate(date = LocalDate.now(), habitId = it.id, accountId = account.id))
        }.subscribe()
    }

    @Test
    @DisplayName("습관 조회 테스트")
    @Order(1)
    fun findAllHabitResult_Success() {
        val flux = habitService.findAllHabitResult(1L, LocalDate.now())

        StepVerifier.create(flux).assertNext {
            assertEquals(it.id, 1L)
            assertEquals(it.isDone, false)
        }.assertNext {
            assertEquals(it.id, 2L)
            assertEquals(it.isDone, true)
        }.assertNext {
            assertEquals(it.id, 3L)
            assertEquals(it.isDone, true)
        }.assertNext {
            assertEquals(it.id, 4L)
            assertEquals(it.isDone, true)
        }.verifyComplete()
    }

    @Test
    @DisplayName("습관 체크 실페 테스트")
    @Order(2)
    fun doHabit_Fail() {
        val mono = habitService.doHabit(account.id!!, 100L, LocalDate.now())

        StepVerifier.create(mono).expectError(NotFoundException::class.java).verify()
    }

    @Test
    @DisplayName("습관 체크 성공 테스트")
    @Order(3)
    fun doHabit_Success() {
        val mono = habitService.doHabit(account.id!!, habit.id!!, LocalDate.now())

        StepVerifier.create(mono).assertNext {
            assertEquals(it.id, habit.accountId)
            assertEquals(it.isDone, true)
        }.verifyComplete()
    }
}