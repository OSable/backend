plugins {
    id("default-module-config")
    id("org.springframework.boot") version "3.0.3"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("plugin.spring") version "1.7.22"
}

java.sourceCompatibility = JavaVersion.VERSION_17
extra["springCloudVersion"] = "2022.0.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")

    // Spring MVC
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Metrics
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    // Eureka Discovery
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // Production Cache - replaces default in-memory Spring cache
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.5")

    // Flexmark Markdown -> HTML
    implementation("com.vladsch.flexmark:flexmark-all:0.64.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// handle spring cloud dependency versions
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}