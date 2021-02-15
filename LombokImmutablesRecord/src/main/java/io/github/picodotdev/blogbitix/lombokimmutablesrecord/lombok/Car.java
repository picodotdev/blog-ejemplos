package io.github.picodotdev.blogbitix.lombokimmutablesrecord.lombok;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Car {

    private String brand;
    private String model;
    private String color;
    private int doors;
    private long kilometers;
}
