package io.github.picodotdev.blogbitix.aspects;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProfileProxy implements InvocationHandler {

    protected Object object;
    protected Proxy proxy;

    public ProfileProxy(Object object) {
        this.object = object;
        proxy = (Proxy) proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }

    public Proxy getProxy() {
        return proxy;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        Object o = method.invoke(object, args);
        long end = System.currentTimeMillis();
        if (method.getName().equals("sleep")) {
            System.out.println("proxy time: " + (end - start));
        }
        return o;
    }
}