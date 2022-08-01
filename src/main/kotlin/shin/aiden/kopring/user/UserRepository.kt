package shin.aiden.kopring.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import shin.aiden.kopring.exception.NotFoundException

fun UserRepository.findByEmailThrow(email: String) = findByEmail(email) ?: throw NotFoundException("not found user")

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}