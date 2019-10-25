package io.github.picodotdev.blogbitix.mutationtesting;

import java.util.List;

public class TicketPriceCalculator {

    public static double FAMILY_DISCOUNT = 0.05;

    private static int ADULT_AGE = 18;
    private static int FREE_TICKET_AGE_BELOW = 3;

    public double calculatePrice(List<Passenger> passengers, int adultTicketPrice, int childTicketPrice) {
        double price = countAdults(passengers) * adultTicketPrice + countChildrens(passengers) * childTicketPrice;
        double discount = (isFamily(passengers)) ? FAMILY_DISCOUNT : 0d;
        return price * (1 - discount);
    }

    private long countAdults(List<Passenger> passengers) {
        return passengers.stream().filter(this::isAdult).count();
    }

    private long countChildrens(List<Passenger> passengers) {
        return passengers.stream().filter(this::isChildren).count();
    }

    private boolean isAdult(Passenger passenger) {
        return passenger.getAge() > ADULT_AGE;
    }

    private boolean isChildren(Passenger passenger) {
        return passenger.getAge() > FREE_TICKET_AGE_BELOW && passenger.getAge() <= ADULT_AGE;
    }

    private boolean isFamily(List<Passenger> passengers) {
        return countAdults(passengers) >= 2 && countChildrens(passengers) >= 2;
    }
}
