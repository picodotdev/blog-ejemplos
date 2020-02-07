package io.github.picodotdev.blogbitix.aspects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Main implements CommandLineRunner {

    @Autowired
    private Foo fooBean;

    @Bean
    public Foo foo() {
        return new Foo();
    }

    @Bean
    public Aspects aspects() {
        return new Aspects();
    }

    @Override
    public void run(String... args) throws Exception {
        // AspectJ
        System.out.println("");
        System.out.println("AspectJ");
        Foo foo = new Foo();
        foo.echo();
        foo.sum(3, 7);
        foo.sleep();

        // Spring AOP
        System.out.println("");
        System.out.println("Spring AOP (AspectJ anotations)");
        fooBean.echo();
        fooBean.sum(3, 7);
        fooBean.sleep();

        // Java Proxy
        System.out.println("");
        System.out.println("Java Proxy");
        IFoo fooProxy = (IFoo) new LogProxy((IFoo) new ProfileProxy(new Foo()).getProxy()).getProxy();
        fooProxy.echo();
        fooProxy.sum(3, 7);
        fooProxy.sleep();
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
