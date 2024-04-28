package net.osable.gateway.resolvers

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.*


/**
 * Gets the requesting client's IP address so that the rate limiter
 * can use it as the key to decide if to reject a request.
 * CF-Connecting-IP -> X-Forwarded-For at Caddy reverse proxy
 */
@Component
class ProxiedClientAddressResolver : KeyResolver {

    override fun resolve(exchange: ServerWebExchange): Mono<String> {
        // maxTrustedIndex represents the number of proxies we control that the requests pass through
        val resolver = XForwardedRemoteAddressResolver.maxTrustedIndex(1)
        val socket = resolver.resolve(exchange)
        return Mono.just(socket.address.hostAddress)
    }

}