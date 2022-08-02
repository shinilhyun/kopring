package shin.aiden.kopring.config.auth

import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.jar.Attributes

class KakaoUser(
 val email: String,
 val nickName: String,
) {
    companion object {
        fun of(oAuth2User: OAuth2User) : KakaoUser{
            val attributes = oAuth2User.attributes
            val kakaoAccount = attributes["kakao_account"] as Map<*, *>
            val email = kakaoAccount["email"] as String
            val properties = attributes["properties"] as Map<*, *>
            val nickname = properties["nickname"] as String
            return KakaoUser(email, nickname)
        }
    }

}