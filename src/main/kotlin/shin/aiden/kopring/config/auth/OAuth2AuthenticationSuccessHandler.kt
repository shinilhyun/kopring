package shin.aiden.kopring.config.auth

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class OAuth2AuthenticationSuccessHandler(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailService: CustomerUserDetailService,
) : SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        //        login 성공한 사용자 목록.
        val oAuth2User: OAuth2User = authentication!!.principal as OAuth2User
        val kakaoUser = KakaoUser.of(oAuth2User)

        val user = userDetailService.loadUserByUsername(kakaoUser.email) as UserPrincipal
        val jwt: String =
            jwtTokenProvider.createAccessToken(user.id.toString(), user.email, listOf(user.role.name))

        val url: String = makeRedirectUrl(jwt)

        if (response!!.isCommitted) {
            logger.debug("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.")
            return
        }
        redirectStrategy.sendRedirect(request, response, url)
    }

    private fun makeRedirectUrl(token: String): String {
        return UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect/$token")
            .build().toUriString()
    }
}