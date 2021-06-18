package io.github.picodotdev.blogbitix.javaconwaylangton.langton;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.fusesource.jansi.Ansi;

class World {

    private Board board;
    private Turmite turmite;
    Queue<Position> positions;
    private Map<Integer, Ansi.Color> palette;
    
    public World() {
        this.board = new Board();
        this.turmite = new Turmite();
        this.positions = new ArrayDeque<>();
        this.palette = new HashMap<>();
        this.palette.put(0, Ansi.Color.GREEN);
        this.palette.put(1, Ansi.Color.WHITE);
        this.palette.put(2, Ansi.Color.YELLOW);
        this.palette.put(3, Ansi.Color.RED);
        this.palette.put(4, Ansi.Color.CYAN);
        this.palette.put(5, Ansi.Color.WHITE);
    }
    
    public void print() {
        positions.forEach(position -> {
            int x = position.getX();
            int y = position.getY();
            Cell cell = board.getCell(x, y);
            if (cell == null) {
                return;
            }
            Ansi.Color color = palette.getOrDefault(cell.getAge(), Ansi.Color.WHITE);
            System.out.print(Ansi.ansi().cursor(y, x).fg(color).a(cell.isOn() ? "X" : " "));
        });
        System.out.print(Ansi.ansi().cursor(turmite.getY(), turmite.getX()).fg(Ansi.Color.WHITE).a("*"));
        System.out.flush();
    }

    public void step() {
        positions.add(turmite.getPosition());
        if (positions.size() > 10) {
            positions.remove();
        }
        turmite.step(board);
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

