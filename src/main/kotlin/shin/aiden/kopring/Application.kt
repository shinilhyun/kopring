package shin.aiden.kopring

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
@EnableWebSecurity
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Bean
fun objectMapper(): ObjectMapper = ObjectMapper().registerModule(kotlinModule())
