package io.github.picodotdev.plugintapestry.services.transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Transactional {
	 Propagation propagation() default Propagation.REQUIRED;
	 int isolation() default -1;
	 boolean readonly() default false;
	 int timeout() default -1;
}