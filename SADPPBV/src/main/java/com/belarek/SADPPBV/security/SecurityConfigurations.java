package com.belarek.SADPPBV.security;

import com.belarek.SADPPBV.util.CustomAccessDeniedHandler;
import com.belarek.SADPPBV.util.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean 
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(crsf -> crsf.disable()).cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/rotas").permitAll()
                        .requestMatchers("/segmentos").hasRole("ADMIN")
                        .requestMatchers("/segmentos/**").hasRole("ADMIN")
                        .requestMatchers("/segmentos/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/segmentos").permitAll()
                        .requestMatchers(HttpMethod.GET,"/pontos").permitAll()
                        .requestMatchers(HttpMethod.POST,"/logout").permitAll()
                        .requestMatchers("/connected-users").permitAll()
                        .requestMatchers("/rotas").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("ADMIN")
                        .requestMatchers("/pontos/**").hasRole("ADMIN")
                        .requestMatchers("/pontos/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/usuarios/{registro}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/usuarios/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/usuarios/**").hasRole("ADMIN")
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(
                                new AntPathRequestMatcher("/login?logout")
                        )
                        .logoutSuccessUrl("/login").permitAll())
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
