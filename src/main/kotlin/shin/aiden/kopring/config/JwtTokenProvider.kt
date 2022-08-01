package shin.aiden.kopring.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import shin.aiden.kopring.exception.UnAuthorizedException
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest


@Component
class JwtTokenProvider(private val userDetailsService: UserDetailsService) {

    private var accessSecret = "myprojectsecret"
    private var refreshSecret = "refreshsecret"

    // 토큰 유효시간 30분
    private val tokenValidTime = 30 * 60 * 1000L
    private val refreshValidTime = 15 * 60 * 60 * 1000L



    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected fun init() {
        accessSecret = Base64.getEncoder().encodeToString(accessSecret.toByteArray())
        refreshSecret = Base64.getEncoder().encodeToString(accessSecret.toByteArray())
    }

    // JWT 토큰 생성
    fun createAccessToken(userPk: String?, email: String?, roles: List<String?>?): String {

        val now = Date()

        return JWT.create()
            .withSubject(userPk)
            .withClaim("email", email)
            .withClaim("roles", roles) // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
            .withIssuedAt(now)
            .withExpiresAt(Date(now.time + tokenValidTime))
            .sign(Algorithm.HMAC256(accessSecret))

    }

    fun createRefreshToken(userPk: String?, roles: List<String?>?): String {

        val now = Date()

        return JWT.create()
            .withSubject(userPk)
            .withIssuedAt(now)
            .withExpiresAt(Date(now.time + refreshValidTime))
            .sign(Algorithm.HMAC256(refreshSecret))

    }

    // JWT 토큰에서 인증 정보 조회
    fun getAuthentication(token: String): Authentication? {
        val userDetails = userDetailsService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    // 토큰에서 회원 정보 추출
    fun getUsername(token: String?): String? {
        return JWT.decode(token).claims["email"].toString()
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
    }

    // 토큰의 유효성 + 만료일자 확인
    fun validateToken(jwtToken: String?): Boolean {
        val verification = JWT.require(Algorithm.HMAC256(accessSecret)).acceptExpiresAt(Date().time).build()
        kotlin.runCatching { verification.verify(jwtToken) }
            .onFailure { throw UnAuthorizedException() }
        return true
    }

    fun validateRefreshToken(jwtToken: String?): Boolean {
        val verification = JWT.require(Algorithm.HMAC256(refreshSecret)).acceptExpiresAt(Date().time).build()

        kotlin.runCatching { verification.verify(jwtToken) }
            .onFailure { throw UnAuthorizedException() }
        return true
    }
}
