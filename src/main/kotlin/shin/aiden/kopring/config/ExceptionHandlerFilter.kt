package shin.aiden.kopring.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import shin.aiden.kopring.exception.ErrorResponse
import shin.aiden.kopring.exception.UnAuthorizedException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class ExceptionHandlerFilter(

) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (ex: UnAuthorizedException) {
            logger.error("exception exception handler filter")
            setErrorResponse(401, response, ex)
        } catch (ex: RuntimeException) {
            logger.error("runtime exception exception handler filter")
            setErrorResponse(401, response, ex)
        }
    }

    fun setErrorResponse(status: Int, response: HttpServletResponse, ex: Throwable) {
        val objectMapper = ObjectMapper().registerModule(kotlinModule())
        response.status = status
        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        val errorResponse = ErrorResponse(401, ex.message)

        val json = objectMapper.writeValueAsString(errorResponse)
        println(json)
        response.writer.write(json)
    }
}