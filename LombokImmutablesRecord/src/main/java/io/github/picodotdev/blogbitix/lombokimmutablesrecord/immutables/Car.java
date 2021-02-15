package io.github.picodotdev.blogbitix.lombokimmutablesrecord.immutables;

import org.immutables.value.Value;

@Value.Immutable
public interface Car {

    String brand();
    String model();
    String color();
    int doors();
    long kilometers();

    class Builder extends ImmutableCar.Builder {}
    static Builder builder() {
        return new Car.Builder();
    }
}
