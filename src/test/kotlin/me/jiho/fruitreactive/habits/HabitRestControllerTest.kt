package me.jiho.fruitreactive.habits

import com.fasterxml.jackson.databind.ObjectMapper
import me.jiho.fruitreactive.accounts.Account
import me.jiho.fruitreactive.accounts.AccountRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import java.time.LocalDate

@SpringBootTest
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class HabitRestControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    private lateinit var habitRepository: HabitRepository

    @Autowired
    private lateinit var habitExecutionDateRepository: HabitExecutionDateRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var account: Account

    private lateinit var habit: Habit

    @BeforeAll
    fun setUp() {
        accountRepository.save(Account(email = "test@email.com", password = "teset")).subscribe { account = it }

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
    @DisplayName("리스트 조회 테스트")
    fun getList() {
        webTestClient.get().uri("/api/habit/list")
            .exchange().expectStatus().isOk.expectBody().consumeWith { print(it) }
    }

    @Test
    @DisplayName("수정 실패 테스트")
    fun ee() {
        val habitRequest = HabitRequest(name = "update", description = "update description")
        webTestClient.put().uri("/api/habit/{habitId}", 100L)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(objectMapper.writeValueAsString(habitRequest))
            .exchange().expectStatus().is5xxServerError.expectBody().consumeWith { print(it) }
    }
}