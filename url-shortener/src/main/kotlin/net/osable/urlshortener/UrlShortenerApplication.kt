package net.osable.urlshortener

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import java.util.concurrent.TimeUnit

@SpringBootApplication
@EnableCaching
class UrlShortenerApplication {

	@Bean fun caffeineConfig() = Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES)

	@Bean fun cacheManager(caffeine: Caffeine<Any, Any>) = CaffeineCacheManager("url").apply { setCaffeine(caffeine) }

}

fun main(args: Array<String>) {
	runApplication<UrlShortenerApplication>(*args)
}
