package net.osable.core.blogs

import org.springframework.web.multipart.MultipartFile

interface StorageService {

    fun save(file: MultipartFile)

    fun load(filename: String): String?

}