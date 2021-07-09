package de.codecentric.springbootadmin.security;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Profile("!nosecurity")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests(
            authorizeRequests ->
                authorizeRequests
                    .antMatchers("/assets/**")
                    .permitAll()
                    .antMatchers("/login")
                    .permitAll()
                    .antMatchers("/actuator/**")
                    .permitAll()
                    .antMatchers(HttpMethod.POST, "/instances")
                    .permitAll()
                    .antMatchers(HttpMethod.DELETE, "/instances/*")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .formLogin(formLogin -> formLogin.loginPage("/login").and())
        .logout(logout -> logout.logoutUrl("/logout"))
        .httpBasic(Customizer.withDefaults())
        .csrf(
            csrf ->
                csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringRequestMatchers(
                        new AntPathRequestMatcher("/actuator/**"),
                        new AntPathRequestMatcher("/instances", HttpMethod.POST.toString()),
                        new AntPathRequestMatcher("/instances/*", HttpMethod.DELETE.toString())));
  }
}
