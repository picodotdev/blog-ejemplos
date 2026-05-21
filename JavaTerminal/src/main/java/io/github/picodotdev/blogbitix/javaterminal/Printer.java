package io.github.picodotdev.blogbitix.javaterminal;

public class Printer {

    private Terminal terminal;
    private int line;

    public Printer(Terminal terminal) {
        this.terminal = terminal;
        this.line = 0;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    synchronized void print(String text, int line) {
        setLine(line);
        erase();
        System.out.print(text);
    }

    void refresh() {
        terminal.refresh();
    }

    private void setLine(int line) {
        String command = "";
        if (this.line < line) {
            command = "B";
        } else if (this.line > line) {
            command = "A";
        }
        if (!command.equals("")) {
            System.out.print(String.format("\033[%s%s", Math.abs(this.line - line), command));
            this.line = line;
        }
    }

    private void erase() {
        System.out.print("0f\33[2K\r");
    }
}