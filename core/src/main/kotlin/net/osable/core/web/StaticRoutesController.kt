package net.osable.core.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class StaticRoutesController {

    @RequestMapping("/") fun rootMapping() = "index"

    @RequestMapping("/privacy") fun privacyMapping() = "privacy"

    @RequestMapping("/tool") fun toolMapping() = "tool"

    @RequestMapping("/security") fun securityMapping() = "security"

    @RequestMapping("open-source") fun openSourceMapping() = "open-source"

    @RequestMapping("/about-us") fun aboutUsMapping() = "about-us"

    @RequestMapping("/about/serpentine") fun serpentineMapping() = "about/serpentine"

}