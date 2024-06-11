package org.example.errors;

public class InvalidBoardInitException extends Exception {
    public InvalidBoardInitException(String message) {
        super(message);
    }
}
