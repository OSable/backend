package net.osable.gateway

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class FallbackController {

    @RequestMapping("/requestsFallback")
    fun `Services Requests Fallback`() = Mono.just("The tea making pipelines that bring you this service from ol’ Blighty are down at the moment but normal service should be resumed as soon possible, Regrettably, OSable Administration")

}