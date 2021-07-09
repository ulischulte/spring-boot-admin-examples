package de.codecentric.springbootadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableAdminServer
public class SpringBootAdminApplication extends SpringBootServletInitializer {

  public static void main(String... args) {
    SpringApplication.run(SpringBootAdminApplication.class);
  }
}
