rootProject.name = "osable-services"
include("core")
include("gateway")
include("discovery")
include("url-shortener")

dependencyResolutionManagement {
    includeBuild("build-logic")
    repositories.gradlePluginPortal()
}
