package me.jiho.fruitreactive.accounts

import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface AccountRepository: R2dbcRepository<Account, Long> {
    fun findByEmail(email: String): Mono<Account>
}