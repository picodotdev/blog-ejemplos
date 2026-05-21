package io.github.picodotdev.blogbitix.jmx.java;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import io.github.picodotdev.blogbitix.jmx.mbean.Hello;

public class Main {

    public static void main(String[] args) throws Exception {
        MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();

        Hello mbean = new Hello();
        ObjectName mbeanName = new ObjectName("io.github.picodotdev.blogbitix:type=Hello");

        mbeanServer.registerMBean(mbean, mbeanName);

        System.out.println("Waiting for incoming requests...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
