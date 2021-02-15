package io.github.picodotdev.blogbitix.lombokimmutablesrecord;

public class Main {

    public static void main(String[] args) {
        {
            io.github.picodotdev.blogbitix.lombokimmutablesrecord.lombok.Car car =
                    io.github.picodotdev.blogbitix.lombokimmutablesrecord.lombok.Car.builder().brand("Tesla").model("Model 3").color("black").doors(5).kilometers(0).build();
            car.setKilometers(10);
            System.out.println("Lombok: " + car);
        }

        {
            io.github.picodotdev.blogbitix.lombokimmutablesrecord.immutables.Car car =
                    io.github.picodotdev.blogbitix.lombokimmutablesrecord.immutables.Car.builder().brand("Tesla").model("Model 3").color("black").doors(5).kilometers(10).build();
            System.out.println("Immutables: " + car);
        }

        {
            io.github.picodotdev.blogbitix.lombokimmutablesrecord.record.Car car =
                    new io.github.picodotdev.blogbitix.lombokimmutablesrecord.record.Car("Tesla", "Model 3", "black", 5, 0);
            System.out.println("Records: " + car);
        }
    }
}
