package com.unicesumar;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.User;
import com.unicesumar.paymentMethods.PaymentType;

import java.util.List;
import java.util.UUID;

public class Sale {
    private final UUID id;
    private final User user;
    private final PaymentType paymentType;
    private final List<Product> products;

    public Sale(User user, PaymentType paymentType, List<Product> products) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.paymentType = paymentType;
        this.products = products;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotal() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
}
