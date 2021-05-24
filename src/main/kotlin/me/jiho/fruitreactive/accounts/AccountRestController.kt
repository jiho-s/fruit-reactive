package me.jiho.fruitreactive.accounts

import me.jiho.fruitreactive.commons.ApiResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/account")
class AccountRestController(private val accountService: AccountService) {

    private val id = 1L

    @GetMapping("/me")
    fun me(): Mono<ApiResult<AccountResponse>> = accountService.findById(id)
        .map(::AccountResponse)
        .map(ApiResult.Companion::success)
}