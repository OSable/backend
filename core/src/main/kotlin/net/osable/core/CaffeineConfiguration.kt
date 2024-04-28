package net.osable.core

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class CaffeineConfiguration {

    @Bean fun caffeineConfig() = Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES)

    @Bean fun cacheManager(caffeine: Caffeine<Any, Any>) = CaffeineCacheManager("blog").apply { setCaffeine(caffeine) }

}