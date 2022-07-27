package com.ensias.bank;

public class InvalidTransactionException extends Exception {

    public InvalidTransactionException(String message) {
        super(message);
    }
}

