package shin.aiden.kopring.user

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class UserDto {
}

data class UserCreateRequest(
    @Email
    val email: String,
    @NotBlank
    val name: String,
    @NotBlank
    var rawPassword: String,
)

data class LoginRequest(
    @Email
    val email: String,
    @NotBlank
    var rawPassword: String,
)