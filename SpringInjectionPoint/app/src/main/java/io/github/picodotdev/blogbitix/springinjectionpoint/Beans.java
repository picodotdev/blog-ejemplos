package io.github.picodotdev.blogbitix.springinjectionpoint;

import java.lang.reflect.Field;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;

@Configuration
public class Beans {

    @Bean
    Logger logger(InjectionPoint ip) {
        Optional<Class> clazzParameter = Optional.of(ip.getMethodParameter()).map(MethodParameter::getContainingClass);
        if (clazzParameter.isPresent()) {
            return LogManager.getLogger(clazzParameter.get());
        }
        Optional<Class> clazzClass = Optional.ofNullable(ip.getField()).map(Field::getDeclaringClass);
        if (clazzClass.isPresent()) {
            return LogManager.getLogger(clazzClass.get());
        }
        throw new IllegalArgumentException();
    }
}
