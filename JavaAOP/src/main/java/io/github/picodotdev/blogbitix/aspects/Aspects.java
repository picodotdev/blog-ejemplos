package io.github.picodotdev.blogbitix.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Around;

@Aspect
public class Aspects {

    @Before("execution(void Foo.echo())")
    public void echoStart() {
        System.out.println("aspect echo begin");
    }

    @After("execution(void Foo.echo())")
    public void echoEnd() {
        System.out.println("aspect echo end");
    }

    @Around("execution(int Foo.sum(int,int))")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("aspect sum begin");
        Object o = pjp.proceed();
        System.out.println("aspect sum end: " + o);
        return o;
    }

    @Around("execution(void Foo.sleep())")
    public void time(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object o = pjp.proceed();
        long end = System.currentTimeMillis();
        System.out.println("aspect time: " + (end - start));
    }
}