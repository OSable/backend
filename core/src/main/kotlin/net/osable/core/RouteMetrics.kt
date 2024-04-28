package net.osable.core

import io.micrometer.core.instrument.Statistic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.metrics.MetricsEndpoint
import org.springframework.stereotype.Component

enum class RouteMetrics(private val endpoint: String, val displayName: String) {

    HOME("/", "Homepage"),
    ADMIN("/admin", "Admin Panel"),
    PRIVACY("/privacy", "Privacy Policy"),
    TOOL("/tool", "Tool"),
    SECURITY("/security", "Security Policy"),
    OPEN_SOURCE("/open-source", "Open Source"),
    ABOUT_US("/about-us", "About Us"),
    ABOUT_SERPENTINE("/about/serpentine", "About Serpentine");

    companion object {
        fun getTotalRequests(metricsEndpoint: MetricsEndpoint) = values().sumOf { it.getRequestsToURL(metricsEndpoint) }
        fun getRequestsPerRoute(metricsEndpoint: MetricsEndpoint) = mutableMapOf<String, Int>().apply {
            values().forEach { this[it.displayName] = it.getRequestsToURL(metricsEndpoint) }
        }
    }

    fun getRequestsToURL(metricsEndpoint: MetricsEndpoint) = metricsEndpoint.requestsToURL(endpoint)

}

fun MetricsEndpoint.requestsToURL(url: String): Int {
    val metric = metric("http.server.requests", listOf("uri:$url"))
    metric ?: return 0
    val count = metric.measurements.find { it.statistic == Statistic.COUNT }
    count ?: return 0
    return count.value.toInt()
}