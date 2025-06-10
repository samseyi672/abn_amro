package com.abn_amro.usermanagment.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAlreadExistExcepton extends RuntimeException {
    public UserAlreadExistExcepton(String message) {
        super(message);
    }
}
