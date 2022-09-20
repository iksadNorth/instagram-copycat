package me.iksadnorth.insta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class JpaConfig implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.of(1L); // TODO: Security 설정 시, 같이 손보기. @CreatedBy를 처리하기 위한 라인이다.
    }
}
