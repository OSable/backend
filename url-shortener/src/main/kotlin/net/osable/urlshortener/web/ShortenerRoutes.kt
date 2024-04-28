package net.osable.urlshortener.web

import net.osable.urlshortener.service.DefaultShortenerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.URI

@RestController
class ShortenerRoutes {

    @Autowired private lateinit var shortenerService: DefaultShortenerService

    @GetMapping("/url/{id}")
    fun redirectToContent(@PathVariable("id") id: String, response: ServerHttpResponse): Mono<Void> {
        response.statusCode = HttpStatus.PERMANENT_REDIRECT
        response.headers.location = URI.create(shortenerService.getURL(id))
        return response.setComplete()
    }

    @PostMapping("/url")
    fun createShortenedURL(@RequestBody url: URL) = URL(shortenerService.shorten(url.url))

}