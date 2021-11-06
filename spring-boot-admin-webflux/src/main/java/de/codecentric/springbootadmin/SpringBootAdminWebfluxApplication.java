package de.codecentric.springbootadmin;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration
@EnableAdminServer
public class SpringBootAdminWebfluxApplication {

    private final AdminServerProperties adminServer;

    public SpringBootAdminWebfluxApplication(AdminServerProperties adminServer) {
        this.adminServer = adminServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminWebfluxApplication.class, args);
    }

    @Bean
    @Profile("insecure")
    public SecurityWebFilterChain securityWebFilterChainPermitAll(ServerHttpSecurity http) {
        return http.authorizeExchange((authorizeExchange) -> authorizeExchange.anyExchange().permitAll())
                .csrf(ServerHttpSecurity.CsrfSpec::disable).build();
    }

    @Bean
    @Profile("secure")
    public SecurityWebFilterChain securityWebFilterChainSecure(ServerHttpSecurity http) {
        return http
                .authorizeExchange((authorizeExchange) -> authorizeExchange
                        .pathMatchers(this.adminServer.path("/assets/**")).permitAll()
                        .pathMatchers(this.adminServer.path("/login")).permitAll().anyExchange().authenticated())
                .formLogin((formLogin) -> formLogin.loginPage(this.adminServer.path("/login")))
                .logout((logout) -> logout.logoutUrl(this.adminServer.path("/logout")))
                .httpBasic(Customizer.withDefaults()).csrf(ServerHttpSecurity.CsrfSpec::disable).build();
    }
}
