package shin.aiden.kopring.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var email: String,
    @Column(name = "password")
    var passwords: String,
    var name: String,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var roles: UserRoles,

) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return arrayListOf(SimpleGrantedAuthority(roles.name))
    }

    override fun getPassword(): String {
        return passwords
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}