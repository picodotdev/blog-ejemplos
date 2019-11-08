package io.github.picodotdev.blogbitix.jmx.mbean;

import org.springframework.stereotype.Component;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedAttribute;

@Component
@ManagedResource(objectName = "io.github.picodotdev.blogbitix:type=Hello")
public class Hello implements HelloMBean {

    @ManagedOperation
    public void sayHello() {
        System.out.println("hello, world");
    }

    @ManagedOperation
    public int add(int x, int y) {
        return x + y;
    }

    @ManagedAttribute
    public String getName() {
        return "Reginald";
    }
}
