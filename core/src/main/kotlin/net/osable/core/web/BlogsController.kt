package net.osable.core.web

import net.osable.core.blogs.DefaultStorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.server.ServerHttpResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView
import reactor.core.publisher.Mono
import java.net.URI

@Controller
class BlogsController {

    @Autowired private lateinit var storageService: DefaultStorageService

    @RequestMapping("/blogs")
    fun blogsPanel(@AuthenticationPrincipal principal: OAuth2User) = "staff/blogs"

    @PostMapping("/blogs/upload")
    fun blogsUpload(@AuthenticationPrincipal principal: OAuth2User, @RequestParam("file") file: MultipartFile, attributes: RedirectAttributes): RedirectView {
        storageService.save(file)

        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView")
        return RedirectView("/blogs/${file.originalFilename?.removeSuffix(".md")}")
    }

    @RequestMapping("/blogs/{blogName}")
    fun loadBlog(@AuthenticationPrincipal principal: OAuth2User, @PathVariable("blogName") blogName: String): ResponseEntity<String> {
        val blog = storageService.load("$blogName.md")
        blog ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        return ResponseEntity.ok(blog)
    }

}