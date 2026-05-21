package io.github.picodotdev.blogbitix.springinjectionpoint;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

public class DefaultApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    System.out.printf("Property (app.property): %s%n", event.getEnvironment().getProperty("app.property"));
  }
}
