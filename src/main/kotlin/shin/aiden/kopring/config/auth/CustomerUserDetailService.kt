package shin.aiden.kopring.config.auth

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import shin.aiden.kopring.user.UserRepository
import shin.aiden.kopring.user.findByEmailThrow

@Service
class CustomerUserDetailService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        checkNotNull(username)
        val user = userRepository.findByEmailThrow(username)
        return UserPrincipal(
            id = user.id,
            name = user.name,
            role = user.role,
            passwords = user.password,
            email = user.email,
            provider = user.provider,
        )
    }

}