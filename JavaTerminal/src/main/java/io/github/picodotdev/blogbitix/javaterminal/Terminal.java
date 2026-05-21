package io.github.picodotdev.blogbitix.javaterminal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Terminal {

    private int width;
    private int heigth;

    public int getWidth() {
        return width;
    }

    public int getHeigth() {
        return heigth;
    }

    synchronized void refresh() {
        try {
            Process colsProcess = new ProcessBuilder("bash", "-c", "tput cols 2> /dev/tty").start();
            Process linesProcess = new ProcessBuilder("bash", "-c", "tput cols 2> /dev/tty").start();
            BufferedReader colsReader = new BufferedReader(new InputStreamReader(colsProcess.getInputStream(), Charset.forName("utf-8")));
            BufferedReader linesReader = new BufferedReader(new InputStreamReader(linesProcess.getInputStream(), Charset.forName("utf-8")));
            String cols = colsReader.readLine();
            String lines = linesReader.readLine();

            width = Integer.parseInt(cols);
            heigth = Integer.parseInt(lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

