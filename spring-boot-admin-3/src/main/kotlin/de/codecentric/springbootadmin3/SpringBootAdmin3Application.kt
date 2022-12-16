package de.codecentric.springbootadmin3

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAdminServer
class SpringBootAdmin3Application

fun main(args: Array<String>) {
	runApplication<SpringBootAdmin3Application>(*args)
}
