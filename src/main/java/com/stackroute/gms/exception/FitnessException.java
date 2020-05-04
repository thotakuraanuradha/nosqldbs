package com.stackroute.gms.exception;

/*This class throws custom fitness exception*/

public class FitnessException extends Exception {

    private String message;

    public FitnessException() {

    }

    public FitnessException(String message) {
        super(message);
    }
}
