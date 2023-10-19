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
    var password: String?,
    var name: String?,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var role: UserRoles,

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    var provider: Provider

)