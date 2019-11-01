package io.github.picodotdev.blogbitix.mutationtesting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

public class TicketPriceCalculatorTest {

    private static int ADULT_TICKET_PRICE = 40;
    private static int CHILD_TICKER_PRICE = 20;

    private TicketPriceCalculator calculator;

    @BeforeEach
    public void before() {
        calculator = new TicketPriceCalculator();
    }

    @Test
    public void calculatePriceForOneAdult() {
        List<Passenger> passengers = new ArrayList<>();
        Passenger passenger = new Passenger(20);
        passengers.add(passenger);
        double price = calculator.calculatePrice(passengers, ADULT_TICKET_PRICE, CHILD_TICKER_PRICE);
        Assertions.assertEquals(ADULT_TICKET_PRICE, price, 0);
    }

    @Test
    public void calculatePriceForChild() {
        List<Passenger> passengers = new ArrayList<>();
        Passenger childPassenger = new Passenger(15);
        passengers.add(childPassenger);
        double price = calculator.calculatePrice(passengers, ADULT_TICKET_PRICE, CHILD_TICKER_PRICE);
        Assertions.assertEquals(CHILD_TICKER_PRICE, price, 0);
    }

    @Test
    public void calculatePriceForFamily() {
        List<Passenger> passengers = new ArrayList<>();
        Passenger adultPassenger1 = new Passenger(20);
        Passenger adultPassenger2 = new Passenger(20);
        Passenger childPassenger3 = new Passenger(12);
        Passenger childPassenger4 = new Passenger(4);
        passengers.add(adultPassenger1);
        passengers.add(adultPassenger2);
        passengers.add(childPassenger3);
        passengers.add(childPassenger4);
        double price = calculator.calculatePrice(passengers, ADULT_TICKET_PRICE, CHILD_TICKER_PRICE);
        Assertions.assertEquals((2 * ADULT_TICKET_PRICE + 2 * CHILD_TICKER_PRICE) * (1 - TicketPriceCalculator.FAMILY_DISCOUNT), price, 0);
    }

    @Test
    public void calculatePriceForNoFamilyByNoAdults() {
        List<Passenger> passengers = new ArrayList<>();
        Passenger adultPassenger1 = new Passenger(20);
        Passenger childPassenger2 = new Passenger(12);
        Passenger childPassenger3 = new Passenger(4);
        passengers.add(adultPassenger1);
        passengers.add(childPassenger2);
        passengers.add(childPassenger3);
        double price = calculator.calculatePrice(passengers, ADULT_TICKET_PRICE, CHILD_TICKER_PRICE);
        Assertions.assertEquals(1 * ADULT_TICKET_PRICE + 2 * CHILD_TICKER_PRICE, price, 0);
    }

    @Test
    public void calculatePriceForNoFamilyByNoChildren() {
        List<Passenger> passengers = new ArrayList<>();
        Passenger adultPassenger1 = new Passenger(20);
        Passenger adultPassenger2 = new Passenger(20);
        Passenger childPassenger3 = new Passenger(12);
        passengers.add(adultPassenger1);
        passengers.add(adultPassenger2);
        passengers.add(childPassenger3);
        double price = calculator.calculatePrice(passengers, ADULT_TICKET_PRICE, CHILD_TICKER_PRICE);
        Assertions.assertEquals(2 * ADULT_TICKET_PRICE + 1 * CHILD_TICKER_PRICE, price, 0);
    }

    @Test
    public void calculatePriceForChildNarrowCase() {
        List<Passenger> passengers = new ArrayList<>();
        Passenger childPassenger = new Passenger(18);
        passengers.add(childPassenger);
        double price = calculator.calculatePrice(passengers, ADULT_TICKET_PRICE, CHILD_TICKER_PRICE);
        Assertions.assertEquals(CHILD_TICKER_PRICE, price, 0);
    }

    @Test
    public void calculatePriceForFreeTicketNarrowCase() {
        List<Passenger> passengers = new ArrayList<>();
        Passenger childPassenger = new Passenger(3);
        passengers.add(childPassenger);
        double price = calculator.calculatePrice(passengers, ADULT_TICKET_PRICE, CHILD_TICKER_PRICE);
        Assertions.assertEquals(0, price, 0);
    }
}