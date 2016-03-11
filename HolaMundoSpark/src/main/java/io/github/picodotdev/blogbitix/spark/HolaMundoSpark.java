package io.github.picodotdev.blogbitix.spark;

import static spark.Spark.get;

public class HolaMundoSpark {

    public static void main(String[] args) {
        get("/hola", (req, res) -> "¡Hola mundo!");
    }
}