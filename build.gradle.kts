import org.jetbrains.kotlin.gradle.plugin.extraProperties

plugins {
    kotlin("jvm") version "1.7.22"
    java
    id("org.springframework.boot") version "3.0.3"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("plugin.spring") version "1.7.22"
}

group = "net.osable"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    // Ktor server
    implementation("io.ktor:ktor-server-core-jvm:2.2.1")
    implementation("io.ktor:ktor-server-netty-jvm:2.2.1")
    implementation("io.ktor:ktor-server-content-negotiation:2.2.1")

    //Ktor client
    implementation("io.ktor:ktor-client-core:2.2.1")
    implementation("io.ktor:ktor-client-cio:2.2.1")
    implementation("io.ktor:ktor-client-content-negotiation:2.2.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}