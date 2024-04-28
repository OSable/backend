package net.osable.core

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

fun main(args: Array<String>) { SpringApplication.run(CoreApplication::class.java, *args) }

@SpringBootApplication
class CoreApplication