package me.jiho.fruitreactive.accounts

import me.jiho.fruitreactive.errors.DuplicateException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AccountServiceTest {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    private lateinit var accountService: AccountService

    lateinit var account: Account

    @BeforeAll
    fun setup() {
        accountRepository.save(Account(email = "test@email.com", password = "test")).subscribe {
            account = it
        }
    }

    @Test
    @DisplayName("계정 생성 성공 테스트")
    fun create_Success() {
        val email = "create@email.com"
        val password = "create"
        val mono = accountService.create(email, password)

        StepVerifier.create(mono).assertNext {
            assertNotNull(it.id)
            assertEquals(it.email, email)
            assertEquals(it.password, password)
        }.verifyComplete()
    }

    @Test
    @DisplayName("계정 생성 실패 테스트")
    fun create_Fail() {
        val mono = accountService.create(account.email, account.password)

        StepVerifier.create(mono).expectError(DuplicateException::class.java).verify()
    }
}