package net.osable.urlshortener.service

import net.osable.urlshortener.data.URLEntity
import net.osable.urlshortener.data.URLRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.security.MessageDigest

@Service
class DefaultShortenerService : ShortenerService {

    @Autowired private lateinit var urlRepository: URLRepository

    /**
     * @param url url to shorten
      * @return Shortened hash of the url provided
     */
    override fun shorten(url: String): String {
        val hashBytes = MessageDigest.getInstance("MD5").digest(url.toByteArray(Charsets.UTF_8))
        val hashString = String.format("%032x", BigInteger(1, hashBytes))
        val shortened = "https://osable.net/url/${hashString.take(6)}"
        urlRepository.save(URLEntity(shortened, url))
        return shortened
    }

    /**
     * @param short Shortened hash to get the original url from
     * @return Original url of the shortened hash provided
     */
    @Cacheable("url")
    override fun getURL(short: String) = urlRepository.findByShortenedURL("https://osable.net/url/$short")

}