package net.osable.core

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.web.bind.annotation.RestController

@Configuration
class SecurityConfiguration {

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
            // Configure CSRF token
            it.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        }.oauth2Client()

        return http.build()
    }

}