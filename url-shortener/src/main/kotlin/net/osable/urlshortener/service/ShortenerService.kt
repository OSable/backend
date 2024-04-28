package net.osable.urlshortener.service

interface ShortenerService {

    fun getURL(short: String): String
    fun shorten(url: String): String

}