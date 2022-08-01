package shin.aiden.kopring.user

class TokenDto {
}

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
