package shin.aiden.kopring.config.auth

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shin.aiden.kopring.user.User
import shin.aiden.kopring.user.UserRepository
import shin.aiden.kopring.user.UserRoles
import java.util.Collections.singleton

@Service
@Transactional
class UserOAuth2Service(
    private val userRepository: UserRepository,

) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {

        val oAuth2User: OAuth2User = super.loadUser(userRequest)
        val attributes = oAuth2User.attributes
        val kakaoUser = KakaoUser.of(oAuth2User)

        userRepository.findByEmail(kakaoUser.email) ?: userRepository.save(User(
            email = kakaoUser.email,
            name = kakaoUser.nickName,
            password = null,
            role = UserRoles.ROLE_USER
        ))

        return DefaultOAuth2User(singleton(SimpleGrantedAuthority(UserRoles.ROLE_USER.name)), attributes, "id");
    }
}