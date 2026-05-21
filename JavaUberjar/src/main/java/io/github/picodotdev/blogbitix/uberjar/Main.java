package io.github.picodotdev.blogbitix.uberjar;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

public class Main {

    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        AnsiConsole.out.println(Ansi.ansi().reset());

        AnsiConsole.out.println(Ansi.ansi().bold().fg(Ansi.Color.RED).a("Hello").fg(Ansi.Color.GREEN).a(" World").fg(Ansi.Color.WHITE).a("!"));

        AnsiConsole.out.println();

        AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.RED    ).a("BBBBB   lll                BBBBB   iii tt    iii       "));
        AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.GREEN  ).a("BB   B  lll  oooo   gggggg BB   B      tt        xx  xx"));
        AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.BLUE   ).a("BBBBBB  lll oo  oo gg   gg BBBBBB  iii tttt  iii   xx  "));
        AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.CYAN   ).a("BB   BB lll oo  oo ggggggg BB   BB iii tt    iii   xx  "));
        AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.MAGENTA).a("BBBBBB  lll  oooo       gg BBBBBB  iii  tttt iii xx  xx"));
        AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW ).a("                    ggggg"));

        AnsiConsole.out.println(Ansi.ansi().reset());
        AnsiConsole.systemUninstall();
    }
}
