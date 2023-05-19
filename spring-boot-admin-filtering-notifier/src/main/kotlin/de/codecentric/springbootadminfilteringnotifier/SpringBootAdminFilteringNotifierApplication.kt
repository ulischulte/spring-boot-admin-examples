package de.codecentric.springbootadminfilteringnotifier

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAdminServer
class SpringBootAdminFilteringNotifierApplication

fun main(args: Array<String>) {
	runApplication<SpringBootAdminFilteringNotifierApplication>(*args)
}
