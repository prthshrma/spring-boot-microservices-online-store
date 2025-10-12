package com.prthshrma.onlinestore.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain springSecurityWebFilterChain(HttpSecurity serverHttpSecurity) throws Exception{
        serverHttpSecurity.csrf(csrf -> {
            try {
                csrf
                        .disable()
                        .authorizeHttpRequests(exchange -> exchange
                                .requestMatchers("/eureka/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                        .httpBasic(withDefaults());
            } catch (Exception e) {
                System.out.println(e);
            }
        });   

        return serverHttpSecurity.build();
    }
}
