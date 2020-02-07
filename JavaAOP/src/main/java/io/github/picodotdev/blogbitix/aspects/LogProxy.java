package io.github.picodotdev.blogbitix.aspects;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LogProxy implements InvocationHandler {

    protected Object object;
    protected Proxy proxy;

    public LogProxy(Object object) {
        this.object = object;
        proxy = (Proxy) proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }

    public Proxy getProxy() {
        return proxy;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy " + method.getName() + " begin");
        Object o = method.invoke(object, args);
        System.out.print("proxy " + method.getName() + " end");
        if (!method.getReturnType().equals(Void.TYPE)) {
            System.out.print(": " + o);
        }
        System.out.println();
        return o;
    }
}