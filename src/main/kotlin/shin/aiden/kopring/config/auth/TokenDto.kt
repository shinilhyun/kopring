package shin.aiden.kopring.config.auth

class TokenDto {
}

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
