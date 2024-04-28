package net.osable.urlshortener.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

// Don't use data classes for JPA Entities
// https://www.baeldung.com/kotlin/jpa

@Entity
class URLEntity(
    @Id val shortenedURL: String,
    @Column val originalURL: String
    ) : java.io.Serializable
