package net.osable.core.web

import net.osable.core.model.ContactRequest
import net.osable.core.model.DiscordRequestBody
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.reactive.function.client.WebClient

@Controller
// Allow cross-origin requests
@CrossOrigin
class FormController {

    private val webClient = WebClient.builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    @Value("#{environment.QUESTIONS_WEBHOOK_URL}")
    private lateinit var webhookURL: String

    @PostMapping("/questions", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], headers = ["Host=api.osable.net"])
    // Method has a void return type, spring MVC tries to find a /questions page to redirect to without this annotation
    @ResponseStatus(HttpStatus.OK)
    fun questionsContactRoute(@ModelAttribute contactRequest: ContactRequest) {
        webClient.post()
            .uri(webhookURL)
            .bodyValue(DiscordRequestBody(
                content = contactRequest.asContentString()
            ))
            .retrieve()
            .toBodilessEntity()
            .subscribe { }
    }

}