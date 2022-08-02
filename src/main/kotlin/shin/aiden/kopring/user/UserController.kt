package shin.aiden.kopring.user

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import shin.aiden.kopring.config.auth.TokenResponse
import shin.aiden.kopring.config.auth.UserPrincipal

@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/sign")
    fun saveUser(@RequestBody userCreateRequest: UserCreateRequest): ResponseEntity<String> {
        userService.saveUser(userCreateRequest)
        return ResponseEntity.ok("success")
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: loginRequest): TokenResponse {
        return userService.login(loginRequest.email, loginRequest.rawPassword)
    }

    @GetMapping("/test")
    fun test(@AuthenticationPrincipal user: UserPrincipal) : String {
        return user.email
    }
}