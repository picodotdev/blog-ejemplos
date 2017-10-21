package io.github.picodotdev.blogbitix.lombok;

public class Main {

    public static void main(String[] args) {
        Car car = Car.builder().brand("Tesla").model("Model 3").color("black").doors(5).kilometers(0).build();

        car.setKilometers(10);
        System.out.println(car);
    }
}

