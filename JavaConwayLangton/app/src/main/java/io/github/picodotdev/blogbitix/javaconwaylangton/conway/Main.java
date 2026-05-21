package io.github.picodotdev.blogbitix.javaconwaylangton.conway;

import org.fusesource.jansi.AnsiConsole;

public class Main {

    private static final String sample =
            "XX \n" +
            " XX\n" +
            " X";

    private static final String dieHard =
            " X     \n" +
            "   X   \n" +
            "XX  XXX";

    public static void main(String[] args) throws Exception {
        AnsiConsole.systemInstall();
        System.out.println();

        World world = new World(dieHard);
        for(int i = 0; i < 11000; ++i) {
            world.print();
            world.step();
            Thread.sleep(25);
        }
    }
}
