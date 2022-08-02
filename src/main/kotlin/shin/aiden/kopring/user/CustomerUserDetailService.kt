package shin.aiden.kopring.user

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

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
        )
    }

}