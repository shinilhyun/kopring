package shin.aiden.kopring.config.auth

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
    private val userOAuth2Service: UserOAuth2Service,
) : WebSecurityConfigurerAdapter() {


    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    override fun configure(http: HttpSecurity?) {

        checkNotNull(http)
        http.sessionManagement { SessionCreationPolicy.STATELESS }
            .csrf().disable()
            .httpBasic().disable()
            .authorizeRequests()// 요청에 대한 사용권한 체크
            .antMatchers("/login/**").permitAll()
            .antMatchers("/oauth2/**").permitAll()
            .antMatchers("/sign").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling()
                .authenticationEntryPoint(AuthEntryPoint())

            .and()
            .oauth2Login()
                .defaultSuccessUrl("/login-success")
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .userInfoEndpoint()
                .userService(userOAuth2Service);
    }

}