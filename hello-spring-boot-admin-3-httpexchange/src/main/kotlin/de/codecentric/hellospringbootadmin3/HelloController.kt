package de.codecentric.hellospringbootadmin3

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/hello")
    fun hello(): String {
        return "Hello Spring Boot 3!"
    }
}
