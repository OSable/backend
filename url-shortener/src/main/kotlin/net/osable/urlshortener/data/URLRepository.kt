package net.osable.urlshortener.data

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface URLRepository : CrudRepository<URLEntity, Long> {

    @Query("SELECT u.originalURL FROM URLEntity u WHERE u.shortenedURL = ?1")
    fun findByShortenedURL(shortenedURL: String): String

}