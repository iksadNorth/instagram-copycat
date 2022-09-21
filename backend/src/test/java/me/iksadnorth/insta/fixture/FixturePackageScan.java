package me.iksadnorth.insta.fixture;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ComponentScan("me.iksadnorth.insta.fixture")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FixturePackageScan {
}
