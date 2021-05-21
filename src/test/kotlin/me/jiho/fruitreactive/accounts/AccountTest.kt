package me.jiho.fruitreactive.accounts

import me.jiho.fruitreactive.FruitReactiveApplication
import me.jiho.fruitreactive.configurations.R2dbcConfiguration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.test.context.ContextConfiguration
import reactor.test.StepVerifier

@DataR2dbcTest
@ContextConfiguration(classes = [R2dbcConfiguration::class, FruitReactiveApplication::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AccountTest {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @BeforeAll
    fun setUp() {
    }

    @Test
    @DisplayName("생성 테스트")
    fun createAccount() {
        val email = "create@email.com"
        val password = "create"
        val mono = accountRepository.save(Account(email = email, password = password))

        StepVerifier.create(mono).assertNext {
            assertEquals(it.email, email)
            assertEquals(it.password, password)
            assertEquals(it.appleInBasket, 0)
            assertEquals(it.appleInTree, 0)
        }.verifyComplete()
    }
}