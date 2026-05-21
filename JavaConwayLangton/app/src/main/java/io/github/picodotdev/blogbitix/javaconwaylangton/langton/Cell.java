package io.github.picodotdev.blogbitix.javaconwaylangton.langton;

public class Cell {

    public enum Status {
        ON, OFF
    }

    private Status status;
    private int age;
    
    public Cell() {
        this.status = Status.OFF;
        this.age = 0;
    }
    
    public boolean isOn() {
        return status == Status.ON;
    }

    public void setOn() {
        this.status = Status.ON;
        this.age = 0;
    }

    public boolean isOff() {
        return status == Status.OFF;
    }
    
    public void setOff() {
        this.status = Status.OFF;
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
        this.status = status;
    }
}