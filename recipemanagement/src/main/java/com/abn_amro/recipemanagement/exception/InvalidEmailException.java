package com.abn_amro.recipemanagement.exception;




public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
