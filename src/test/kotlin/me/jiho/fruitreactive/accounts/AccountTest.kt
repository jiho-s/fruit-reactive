package me.jiho.fruitreactive.accounts

import me.jiho.fruitreactive.FruitReactiveApplication
import me.jiho.fruitreactive.configurations.R2dbcConfiguration
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.test.context.ContextConfiguration
import reactor.test.StepVerifier

@DataR2dbcTest
@ContextConfiguration(classes = [R2dbcConfiguration::class, FruitReactiveApplication::class])
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AccountTest {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    private lateinit var account: Account

    @BeforeAll
    fun setUp() {
        accountRepository.save(Account(email = "test@email.com", password = "pass")).subscribe{
            account = it
        }
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

    @Test
    @DisplayName("이메일로 조회 성공 테스트")
    fun findByEmail_Success() {
        val mono = accountRepository.findByEmail(account.email)

        StepVerifier.create(mono).assertNext {
            assertEquals(it.email, account.email)
            assertEquals(it.password, account.password)
            assertEquals(it.id, account.id)
        }.verifyComplete()
    }

    @Test
    @DisplayName("이메일로 조회 실패 테스트")
    fun findByEmail_Fail() {
        val mono = accountRepository.findByEmail("notvalid@email.com")

        StepVerifier.create(mono).expectComplete().verify()
    }

    @Test
    @DisplayName("id로 조회 성공 테스트")
    fun findById_Success() {
        val mono = accountRepository.findById(account.id!!)

        StepVerifier.create(mono).assertNext {
            assertEquals(it.id, account.id)
            assertEquals(it.password, account.password)
            assertEquals(it.email, account.email)
        }.verifyComplete()
    }

    @Test
    @DisplayName("account 변경 성공 테스트")
    fun update_Success() {
        accountRepository.save(Account(id = account.id, email = "after@email.com", password = "after"))
            .`as`(StepVerifier::create).assertNext {
                assertEquals(it.id, account.id)
                assertNotEquals(it.email, account.email)
                assertNotEquals(it.password, account.password)
            }.verifyComplete()
    }
}