package shin.aiden.kopring.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shin.aiden.kopring.config.auth.JwtTokenProvider
import shin.aiden.kopring.config.auth.TokenResponse

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val tokenProvider: JwtTokenProvider,
) {
    fun UserCreateRequest.create() = User(
        email = this.email,
        name = this.name,
        password = bCryptPasswordEncoder.encode(this.rawPassword),
        role = UserRoles.ROLE_USER,
        provider = Provider.INVIGO,
    )

    fun saveUser(userCreateRequest: UserCreateRequest) {
        val user = userRepository.findByEmail(userCreateRequest.email)
        if (user != null) {
            throw IllegalArgumentException("이미 사용중인 email 입니다")
        }
        userRepository.save(userCreateRequest.create())
    }

    @Transactional(readOnly = true)
    fun login(email: String, rawPassword: String) : TokenResponse {
        val user = userRepository.findByEmailThrow(email)
        val matches = bCryptPasswordEncoder.matches(rawPassword, user.password)
        if(!matches) throw IllegalArgumentException("패스워드가 일치하지 않습니다")
        val accessToken = tokenProvider.createAccessToken(user.id.toString(), user.email, arrayListOf(user.role.name))
        val refreshToken = tokenProvider.createRefreshToken(user.id.toString(), arrayListOf(user.role.name))
        return TokenResponse(accessToken = accessToken, refreshToken = refreshToken)
    }
}