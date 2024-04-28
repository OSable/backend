import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("default-module-config")
    id("org.springframework.boot") version "3.0.3"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("plugin.spring") version "1.7.22"
}

java.sourceCompatibility = JavaVersion.VERSION_17
extra["springCloudVersion"] = "2022.0.1"

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")

    // Failover
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")

    // Load Balancing
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // Production Cache - replaces default in-memory Spring cache
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.5")

    // Redis for rate limiting cache
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// handle spring cloud dependency versions
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}