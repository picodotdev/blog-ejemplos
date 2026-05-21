package io.github.picodotdev.blogbitix.javaee.ejb;

import java.math.BigDecimal;

public interface TheatreBookerRemote {

    void createCustomer();

    BigDecimal getAccountBalance();

    void bookSeat(int idSeat) throws SeatBookedException, NotEnoughMoneyException, NoSuchSeatException;

}