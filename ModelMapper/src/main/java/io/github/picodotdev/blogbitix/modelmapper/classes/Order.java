package io.github.picodotdev.blogbitix.modelmapper.classes;

public class Order {

    private Customer customer;
    private Address billingAddress;

    public Order(Customer customer, Address billingAddress) {
        this.customer = customer;
        this.billingAddress = billingAddress;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }
}