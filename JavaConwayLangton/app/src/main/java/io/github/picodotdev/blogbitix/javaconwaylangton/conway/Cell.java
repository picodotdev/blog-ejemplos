package io.github.picodotdev.blogbitix.javaconwaylangton.conway;

public class Cell {

    public enum Status {
        ALIVE, DEAD
    }

    private Status status;
    private int age;
    
    public Cell() {
        this(Status.DEAD);
    }

    public Cell(Status status) {
        this(status, 0);
    }

    public Cell(Status status, int age) {
        this.status = status;
        this.age = age;
    }
    
    public boolean isAlive() {
        return status == Status.ALIVE;
    }

    public void setAlive() {
        this.status = Status.ALIVE;
        this.age = 0;
    }

    public boolean isDead() {
        return status == Status.DEAD;
    }
    
    public void setDead() {
        this.status = Status.DEAD;
        this.age = 0;
    }

    public int getAge() {
        return age;
    }

    public void tick() {
        age += 1;
    }

    public Status getStatus() {
        return status;
    }
        
    public void setStatus(Status status) {
        if (this.status != status) {
            age = 0;
        }
        this.status = status;
    }
}
