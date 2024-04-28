package net.osable.core.web

import net.osable.core.RouteMetrics
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.metrics.MetricsEndpoint
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class AdminController {

    @Autowired private lateinit var metricsEndpoint: MetricsEndpoint

    @RequestMapping("/admin")
    fun adminPanel(@AuthenticationPrincipal principal: OAuth2User) = ModelAndView("staff/admin").apply {
        addObject("username", principal.attributes["login"])
        addObject("visitCount", RouteMetrics.ADMIN.getRequestsToURL(metricsEndpoint))
        addObject("routeMetrics", RouteMetrics.getRequestsPerRoute(metricsEndpoint))
    }

}