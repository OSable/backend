package net.osable.gateway

import net.osable.gateway.resolvers.ProxiedClientAddressResolver
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus

fun main(args: Array<String>) { SpringApplication.run(GatewayApplication::class.java, *args) }

@SpringBootApplication
class GatewayApplication {

    /*
     * Ignore this warning, the program runs fine, there is no issue. IntelliJ's inspection is wrong.
     * Tried @SuppressWarnings("SpringJavaAutowiringInspection", "SpringJavaInjectionPointsAutowiringInspection")
     * "Incorrect injection point autowiring in Spring bean components" is the inspection in IJ settings
     * You can turn that from an error to a warning to not go insane :)
     */
    @Bean fun declareGatewayRoutes(builder: RouteLocatorBuilder): RouteLocator = builder.routes()
        // Block external access to actuator endpoints while still having them accessible locally
        .route { route ->
            route.path("/actuator/**").filters { it.setStatus(HttpStatus.FORBIDDEN) }.uri("no://op")
        }
        // URL Shortener route configuration
        .route { route ->
            route.host("api.osable.net").and()
            .path("/url")
                .filters { filters ->
                    filters.circuitBreaker { fallbackConfig ->
                        fallbackConfig.name = "urlShortenerCreationFallback"
                        fallbackConfig.setFallbackUri("forward:/requestsFallback")
                    }

                    filters.configureRedisRateLimiterFilter(1)
                }.uri("lb://url-shortener/url")
        }
//        // URL Shortener routes configuration
        .route { route ->
            route.host("api.osable.net").and()
            .path("/url/**")
                .filters { filters ->
                    filters.circuitBreaker { fallbackConfig ->
                        fallbackConfig.name = "urlShortenerRequestsFallback"
                        fallbackConfig.setFallbackUri("forward:/requestsFallback")
                    }

                    filters.configureRedisRateLimiterFilter(5)
                }.uri("lb://url-shortener/url/")
        }
        // All other routes should go to core
        .route { route ->
            route.path("/**")
                .filters { filters ->
                    filters.circuitBreaker { fallbackConfig ->
                        fallbackConfig.name = "coreRequestsFallback"
                        fallbackConfig.setFallbackUri("forward:/requestsFallback")
                    }

                    filters.configureRedisRateLimiterFilter(10)
                }.uri("lb://core/")
        }
        .build()

    fun GatewayFilterSpec.configureRedisRateLimiterFilter(requestsPerSecond: Int) = requestRateLimiter().rateLimiter(RedisRateLimiter::class.java) {
        it.replenishRate = requestsPerSecond
        it.burstCapacity = requestsPerSecond
        it.requestedTokens = 1
    }.configure {
        it.keyResolver = ProxiedClientAddressResolver()
    }

}