package io.github.picodotdev.blogbitix.javaconwaylangton.conway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Board {

    private Collection<Integer> aliveRules = List.of(2, 3);
    private Collection<Integer> bornRules = List.of(3);
    private Cell[][] cells;

    public Board(String initial) {
        this(initial, 160, 80);
    }

    public Board(String initial, int width, int height) {
        initCells(width, height);
        loadCells(initial);
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
    
    public int getWidth() {
        return cells[0].length;
    }
    
    public int getHeight() {
        return cells.length;
    }

    public int getPopulation() {
        int population = 0;
        for (int y = 0; y < getHeight(); ++y) {
            for (int x = 0; x < getWidth(); ++x) {
                if (getCell(x, y).isAlive()) {
                    population += 1;
                }
            }
        }
        return population;
    }
    
    public void step() {
        Cell[][] cells = new Cell[getHeight()][getWidth()];
        for (int y = 0; y < getHeight(); ++y) {
            for (int x = 0; x < getWidth(); ++x) {
                Cell oldCell = getCell(x, y);
                Cell newCell = new Cell(oldCell.getStatus(), oldCell.getAge());
                cells[y][x] = newCell;
                int aliveNeighbours = countAliveNeighbours(x, y);
                Cell.Status status = (survives(oldCell, aliveNeighbours) || borns(oldCell, aliveNeighbours)) ? Cell.Status.ALIVE : Cell.Status.DEAD;
                newCell.setStatus(status);
            }
        }
        this.cells = cells;
    }
    
    private boolean survives(Cell cell, int aliveNeighbours) {
        return cell.isAlive() && aliveRules.contains(aliveNeighbours);
    }
 
    private boolean borns(Cell cell, int aliveNeighbours) {
        return cell.isDead() && bornRules.contains(aliveNeighbours);
    }
    
    private int countAliveNeighbours(int x, int y) {
        return (int) getNeighbours(x, y).stream().filter(Cell::isAlive).count();
    }
    
    private Collection<Cell> getNeighbours(int x, int y) {
        Collection<Position> positions = new ArrayList<>();
        for (int j = y - 1; j < y + 2; ++j) {
            for (int i = x - 1; i < x + 2; ++i) {
                if (i == x && j == y) {
                    continue;
                }
                if (i < 0 || i > getWidth() - 1 || j < 0 || j > getHeight() - 1) {
                    continue;
                }
                positions.add(new Position(i, j));
            }
        }
        return positions.stream().map(p -> getCell(p.getX(), p.getY())).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private void initCells(int width, int height) {
        this.cells = new Cell[height][width];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                this.cells[y][x] = new Cell(Cell.Status.DEAD);
            }
        }
    }

    private void loadCells(String initial) {
        int width = Arrays.stream(initial.split("\\n")).max(Comparator.comparing(s -> s.length())).orElseGet(() -> "").length();
        int height = initial.split("\\n").length;

        int x = (getWidth() / 2) - (width / 2);
        int y = (getHeight() / 2) - (height / 2);

        for (int i = 0, a = 0, b = 0; i < initial.length(); ++i) {
            Character c = initial.charAt(i);
            if (c == '\n') {
                a = 0;
                b += 1;
            } else if (c != ' ') {
                Cell cell = getCell(x + a, y + b);
                cell.setStatus(Cell.Status.ALIVE);
                a += 1;
            } else {
                a += 1;
            }
        }
    }
}