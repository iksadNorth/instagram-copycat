package me.iksadnorth.insta.config;

import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.config.filter.JwtTokenFilter;
import me.iksadnorth.insta.config.properies.JwtProperties;
import me.iksadnorth.insta.exception.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {

    @Autowired UserDetailsService userDetailsService;
    @Autowired JwtProperties jwtProperties;

    @Value("${cors.allowed-url}") String allowedUrl;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .httpBasic().disable().formLogin().disable()
                .cors().configurationSource(corsConfig()).and()
                .headers().frameOptions().disable().and()       // TODO: h2 console을 사용하기 위한 설정이므로 최종 결과물에서는 제거하기.

                .authorizeHttpRequests()
                    .mvcMatchers(HttpMethod.DELETE, "/api/v1/hashtag/*").hasRole("ADMIN")
                    .mvcMatchers(HttpMethod.DELETE, "/api/v1/**").hasAnyRole("USER", "ADMIN")
                    .mvcMatchers(HttpMethod.PUT, "/api/v1/**").hasAnyRole("USER", "ADMIN")
                    .mvcMatchers(HttpMethod.GET, "/api/v1/accounts/*/articles/recommended").hasAnyRole("USER", "ADMIN")
                    .mvcMatchers(HttpMethod.POST,
                            "/api/v1/articles",
                            "/api/v1/articles/*/comments",
                            "/api/v1/articles/*/view",
                            "/api/v1/articles/*/like",
                            "/api/v1/articles/*/hashtag"
                    ).hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll().and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()).and()

                .addFilterBefore(new JwtTokenFilter(userDetailsService, jwtProperties.getSecretKey()), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfig() {
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin(allowedUrl);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/v1/**", config);
        return source;
    }
}