package net.osable.core.web

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.resource.PathResourceResolver

@Configuration
class StaticResourceRoutesConfiguration : WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {

        // Configure static CSS routes
        registry.addResourceHandler("/assets/css/**")
            .addResourceLocations("classpath:/frontend/css/")
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(PathResourceResolver())

        // Configure static image routes
        registry.addResourceHandler("/assets/images/**")
            .addResourceLocations("classpath:/frontend/images/")
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(PathResourceResolver())

        // Configure static js routes
        registry.addResourceHandler("/assets/js/**")
            .addResourceLocations("classpath:/frontend/js/")
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(PathResourceResolver())

    }

}