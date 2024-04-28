package net.osable.core

import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector
import reactor.core.publisher.Mono

class GitHubReactiveOpaqueTokenIntrospector : ReactiveOpaqueTokenIntrospector {
    override fun introspect(token: String): Mono<OAuth2AuthenticatedPrincipal> {
        println("Token: $token")
        return Mono.just(DefaultOAuth2AuthenticatedPrincipal(mapOf(), null))
    }

}