package com.prthshrma.onlinestore.discovery_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig{

    // @Bean
    // public UserDetailsService userDetailsService(){
    //     return new InMemoryUserDetailsManager(
    //         User.withUsername("eureka")
    //         .password("{noop}password")
    //         .authorities("USER")
    //         .build()
    //     );
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("eureka/**"));

        return httpSecurity.build();
    }
}
