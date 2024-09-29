package net.osable.core.web

import net.osable.core.model.RawData
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class ToolController {

    @PostMapping("/raw", headers = ["Host=api.osable.net"])
    fun rawMapping(@RequestBody rawData: RawData) {
        // Do something once the tool actually exists.
    }

}