package me.jiho.fruitreactive.configurations

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.MessageSourceAccessor

@Configuration
class ServiceConfiguration {

    @Bean
    fun messageSourceAccessor(messageSource: MessageSource): MessageSourceAccessor{
        return MessageSourceAccessor(messageSource)
    }
}