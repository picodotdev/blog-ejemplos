package io.github.picodotdev.blogbitix.javaconwaylangton.conway;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.fusesource.jansi.Ansi;

class World {

    private Board board;
    private int generation;
    private Map<Integer, Ansi.Color> palette;

    public World(String initial) {
        this.board = new Board(initial);
        this.generation = 0;
        this.palette = new HashMap<>();
        this.palette.put(0, Ansi.Color.GREEN);
        this.palette.put(1, Ansi.Color.WHITE);
        this.palette.put(2, Ansi.Color.YELLOW);
        this.palette.put(3, Ansi.Color.RED);
        this.palette.put(4, Ansi.Color.CYAN);
        this.palette.put(5, Ansi.Color.WHITE);
    }
    
    public void print() {
        for (int y = 0; y < board.getHeight(); ++y) {
            for (int x = 0; x < board.getWidth(); ++x) {
                Cell cell = board.getCell(x, y);
                if (cell == null) {
                    return;
                }
                Ansi.Color color = palette.getOrDefault(cell.getAge(), Ansi.Color.WHITE);
                System.out.print(Ansi.ansi().cursor(y, x).fg(color).a(cell.isAlive() ? "X" : " "));
            }
        }
        System.out.print(Ansi.ansi().cursor(1, 0).fg(Ansi.Color.WHITE).a("Generación: " + generation));
        System.out.print(Ansi.ansi().cursor(2, 0).fg(Ansi.Color.WHITE).a("Poblacion: " + board.getPopulation()));
        System.out.flush();
    }

    public void step() {
        generation += 1;
        board.step();
        tick();
    }

    private void tick() {
        for (int y = 0; y < board.getHeight(); ++y) {
            for (int x = 0; x < board.getWidth(); ++x) {
                board.getCell(x, y).tick();
            }
        }
    }
}

