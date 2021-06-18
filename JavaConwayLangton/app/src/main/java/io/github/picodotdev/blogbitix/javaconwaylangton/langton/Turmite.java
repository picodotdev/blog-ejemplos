package io.github.picodotdev.blogbitix.javaconwaylangton.langton;

public class Turmite {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private int x;
    private int y;
    private Direction direction;

    public Turmite() {
        this(80, 24, Direction.UP);
    }
    
    public Turmite(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position getPosition() {
        return new Position(x, y);
    }

    public boolean isAt(int x, int y) {
        return this.x == x && this.y == y;
    }
    
    public void step(Board board) {
        Cell cell = board.getCell(x, y);
        if (cell == null) {
            return;
        }
        if (cell.isOn()) {
            cell.setOff();
            turnLeft();
            forward();
        } else if (cell.isOff()) {
            cell.setOn();
            turnRight();
            forward();
        }
    }
    
    public void forward() {
        switch (direction) {
            case UP:
                this.y -= 1;
                break;
            case DOWN:
                this.y += 1;
                break;
            case LEFT:
                this.x -= 1;
                break;
            case RIGHT:
                this.x += 1;
                break;
        }
    }
    
    public void turnLeft() {
        switch (direction) {
            case UP:
                this.direction = Direction.LEFT;
                break;
            case DOWN:
                this.direction = Direction.RIGHT;
                break;
            case LEFT:
                this.direction = Direction.DOWN;
                break;
            case RIGHT:
                this.direction = Direction.UP;
                break;
        }
    }
    
    public void turnRight() {
        switch (direction) {
            case UP:
                this.direction = Direction.RIGHT;
                break;
            case DOWN:
                this.direction = Direction.LEFT;
                break;
            case LEFT:
                this.direction = Direction.UP;
                break;
            case RIGHT:
                this.direction = Direction.DOWN;
                break;
        }
    }
}