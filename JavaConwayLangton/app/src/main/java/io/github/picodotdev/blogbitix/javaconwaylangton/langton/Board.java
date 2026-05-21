package io.github.picodotdev.blogbitix.javaconwaylangton.langton;

public class Board {

    private Cell[][] cells;

    public Board() {
        this(160, 48);
    }

    public Board(int width, int height) {
        this.cells = new Cell[height][width];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                this.cells[y][x] = new Cell();
            }
        }
    }
    
    public int getWidth() {
        return cells[0].length;
    }
 
    public int getHeight() {
        return cells.length;
    }
    
    public Cell getCell(int x, int y) {
        if (y < 0 || y > getHeight()) {
            return null;
        }
        if (x < 0 || x > getWidth()) {
            return null;
        }
        return cells[y][x];
    }
}