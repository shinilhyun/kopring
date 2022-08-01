package shin.aiden.kopring.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import shin.aiden.kopring.exception.ErrorResponse
import shin.aiden.kopring.objectMapper
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthEntryPoint : AuthenticationEntryPoint {
    private val logger = KotlinLogging.logger {  }
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        logger.error("UnAuthorizaed!!! message : " + authException?.message)
        checkNotNull(response)
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        response.setStatus(401)

        val json =
            objectMapper().registerModule(kotlinModule()).writeValueAsString(ErrorResponse(401, "인증오류"))
        response.writer.println(json)

    }
}