package io.github.picodotdev.blogbitix.drools;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.dmn.api.core.DMNRuntime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    KieContainer kieContainer() {
        KieServices kieServices = KieServices.Factory.get();
        return kieServices.getKieClasspathContainer();
    }

    @Bean
    DMNRuntime dnmRuntime(KieContainer kieContainer) {
        return KieRuntimeFactory.of(kieContainer.getKieBase()).get(DMNRuntime.class);
    }
}
