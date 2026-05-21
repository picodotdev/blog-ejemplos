package io.github.picodotdev.blogbitix.javaterminal;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Progress implements Runnable {

    private Printer printer;
    private int line;
    private int seconds;

    public Progress(Printer printer, int line, int seconds) {
        this.printer = printer;
        this.line = line;
        this.seconds = seconds;
    }

    public void run() {
        long duration = seconds * 1000;
        long start = System.currentTimeMillis();
        long now = start;
        while (start + duration > now) {
            printer.refresh();

            now = System.currentTimeMillis();
            long percent = Math.min(100, Math.round(((double) (now - start) / duration) * 100));
            int size = printer.getTerminal().getWidth() - 11 - 8 - 2;
            long characters = Math.round((double) (size * percent / 100));
            char[] chars = new char[size];
            for (int i = 0; i < chars.length; ++i) {
                chars[i] = (i < characters) ? '#' : '-';
            }

            String nameStatus = "jdk-openjdk";
            String progressStatus = String.valueOf(chars);
            String percentStatus = String.valueOf(percent) + "%";

            String status = String.format("%-11s [%s] %s", nameStatus, progressStatus, percentStatus);
            printer.print(status, line);
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
