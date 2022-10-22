package me.iksadnorth.insta.config;

import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.entity.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public AuditorAware<Account> auditorAware() {
        return () -> Optional.ofNullable((SecurityContextHolder.getContext()))
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .filter(auth -> !auth.getName().equals("anonymousUser"))
                .map(Authentication::getPrincipal)
                .map(AccountDto.class::cast)
                .map(AccountDto::toEntity);
    }
}
