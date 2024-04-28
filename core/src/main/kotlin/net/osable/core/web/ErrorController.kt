package net.osable.core.web

import jakarta.servlet.http.HttpServletRequest
import net.osable.core.Error
import net.osable.core.getErrorCode
import net.osable.core.getHttpStatusMessage
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class ErrorController : org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error") fun defaultErrorMapping(request: HttpServletRequest) = ModelAndView("templates/error").apply {
        addObject("error", Error(request.getErrorCode(), getHttpStatusMessage(request.getErrorCode())))
    }

}