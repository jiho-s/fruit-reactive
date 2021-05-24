package me.jiho.fruitreactive.accounts

import me.jiho.fruitreactive.errors.DuplicateException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class AccountService(private val accountRepository: AccountRepository) {

    @Transactional(readOnly = true)
    fun findById(id: Long): Mono<Account> = Mono.from(accountRepository.findById(id))

    @Transactional(readOnly = true)
    fun findByEmail(email: String): Mono<Account> = Mono.from(accountRepository.findByEmail(email))

    @Transactional
    fun create(email: String, password: String): Mono<Account> = validateEmail(email).flatMap { valid ->
        if (!valid) {
            Mono.error(DuplicateException(Account::class, email))
        } else {
            accountRepository.save(Account(email = email, password = password))
        }
    }

    @Transactional(readOnly = true)
    fun validateEmail(email: String) : Mono<Boolean> =
        Mono.from(accountRepository.findByEmail(email)).hasElement().map { !it }
}