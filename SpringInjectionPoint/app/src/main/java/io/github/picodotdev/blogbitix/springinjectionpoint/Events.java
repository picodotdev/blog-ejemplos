package io.github.picodotdev.blogbitix.springinjectionpoint;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

public class Events implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    System.out.println(event.getEnvironment().getProperty("app.property"));
  }
}
