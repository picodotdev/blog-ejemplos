package io.github.picodotdev.blogbitix.springbootconfigconditional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import io.github.picodotdev.blogbitix.springbootconfigconditional.conditional.OnLinuxCondition;
import io.github.picodotdev.blogbitix.springbootconfigconditional.conditional.OperatingSystem;

@Component
public class Beans {

    @Bean
    @Conditional(OnLinuxCondition.class)
    OperatingSystem linuxSystemBean() {
        return new OperatingSystem("Linux");
    }

    @Bean
    @ConditionalOnMissingBean(value = OperatingSystem.class)
    OperatingSystem windowsSystemBean() {
        return new OperatingSystem("Other");
    }
}
