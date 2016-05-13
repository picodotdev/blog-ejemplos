package io.github.picodotdev.plugintapestry.misc;

public class Globals {

    public static ThreadLocal<String> HOST;

    static {
        HOST = new ThreadLocal<>();
    }
}
