package de.codecentric.hellospringbootadmin3

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAdminServer
class HelloSpringBootAdmin3HttpExchange

fun main(args: Array<String>) {
	runApplication<HelloSpringBootAdmin3HttpExchange>(*args)
}
