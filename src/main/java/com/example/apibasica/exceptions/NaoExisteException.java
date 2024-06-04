package com.example.apibasica.exceptions;

public class NaoExisteException extends RuntimeException {

    public NaoExisteException(String message) {
        super(message);
    }

}
