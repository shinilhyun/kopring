package shin.aiden.kopring.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class ErrorResponse(
    val code: Int,
    val message: String?,
)