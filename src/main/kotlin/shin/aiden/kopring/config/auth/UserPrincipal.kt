package shin.aiden.kopring.config.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import shin.aiden.kopring.user.UserRoles

class UserPrincipal(
    var id: Long? = null,
    var email: String,
    var passwords: String?,
    var name: String?,
    var role: UserRoles,
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return arrayListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String? {
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