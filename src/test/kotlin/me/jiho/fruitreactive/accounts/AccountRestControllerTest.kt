package me.jiho.fruitreactive.accounts

import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AccountRestControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var accountService: AccountService

    lateinit var account: Account

    @BeforeAll
    fun setup() {
        accountService.create("test@email.com", "test").subscribe {
            account = it
        }
    }

    @Test
    @DisplayName("계정 조회 성공")
    fun me() {
        val response = webTestClient.get()
            .uri("/api/account/me")
            .accept(MediaType.APPLICATION_JSON).exchange()

        response
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.success", `is`(true)).isBoolean
            .jsonPath("$.response").isNotEmpty
            .jsonPath("$.response.id", `is`(account.id)).isNotEmpty
            .jsonPath("$.response.email", `is`(account.email)).isNotEmpty
            .jsonPath("$.response.password").doesNotExist()
            .jsonPath("$.response.appleInTree").exists()
            .jsonPath("$.response.appleInBasket").exists()
            .jsonPath("$.error").isEmpty
            .consumeWith { print(it) }
    }
}