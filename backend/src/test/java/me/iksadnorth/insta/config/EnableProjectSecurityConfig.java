package me.iksadnorth.insta.config;

import me.iksadnorth.insta.config.properies.JwtProperties;
import me.iksadnorth.insta.service.AccountService;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Import({AuthenticationConfig.class, JwtProperties.class, AccountService.class})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableProjectSecurityConfig {
}
