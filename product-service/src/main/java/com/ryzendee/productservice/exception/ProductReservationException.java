package com.ryzendee.productservice.exception;

public class ProductReservationException extends RuntimeException {

    public ProductReservationException(String message) {
        super(message);
    }

    public ProductReservationException(String message, Throwable cause) {
        super(message, cause);
    }
}
