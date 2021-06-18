package io.github.picodotdev.blogbitix.javaconwaylangton.langton;

import org.fusesource.jansi.AnsiConsole;

public class Main {

    public static void main(String[] args) throws Exception {
        AnsiConsole.systemInstall();
        System.out.println();

        World world = new World();
        for(int i = 0; i < 11000; ++i) {
            world.print();
            world.step();
            Thread.sleep(25);
        }
    }
}
