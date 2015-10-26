package io.github.picodotdev.blogbitix.springboot.validator;
 
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.github.picodotdev.blogbitix.springboot.jooq.tables.interfaces.IEmployee;

@Component
public class EmployeeValidator implements Validator {
 
    public boolean supports(Class<?> clazz) {
        return IEmployee.class.isAssignableFrom(clazz);
    }
 
    public void validate(Object target, Errors errors) {
    	if (!supports(target.getClass())) {
    		return;
    	}
    	IEmployee o = (IEmployee) target;
    	if (StringUtils.isBlank(o.getName())) {
    		errors.rejectValue("name", "NotNull", new Object[] { o.getName() }, "name cannot be null");
    	}
    	if (o.getBirthday() != null && o.getBirthday().isAfter(LocalDateTime.now())) {
    		errors.rejectValue("birthdate", "invalid", new Object[]{ o.getBirthday() }, "birthdate cannot be after now");
    	}
    }
}