package net.osable.core

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfiguration {

    @Autowired private lateinit var environment: Environment

    @Bean fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests {

            // Configure access to /admin
            it.requestMatchers("/admin").fullyAuthenticated()

            // Configure access to blog upload
            it.requestMatchers("/blogs", "/blogs/upload").fullyAuthenticated()

            // Configure access to the rest of the site
            it.requestMatchers("/**").permitAll()
                .anyRequest().authenticated()

        }.csrf {
            // Configure CSRF token through a cookie
            // Setting domain allows sending the cookie on that domain **and subdomains**
            it.csrfTokenRepository(CookieCsrfTokenRepository().apply {
                setSecure(true)
                setCookieHttpOnly(true)
                setCookieDomain("osable.net")
            })
        }.oauth2Client()


        http.exceptionHandling().accessDeniedHandler { request, response, accessDeniedException ->
            println("Access denied. Cause: ${accessDeniedException.cause} | Message: ${accessDeniedException.message}")
            accessDeniedException.printStackTrace()
            response.status = HttpStatus.FORBIDDEN.value()
        }

        http.cors().configurationSource {
            CorsConfiguration()
                .applyPermitDefaultValues()
                .apply {
                    allowedOrigins = listOf("https://osable.net")
                }
        }

        return http.build()
    }

}