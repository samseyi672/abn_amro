package com.abn_amro.usermanagment.exceptions;

import org.aspectj.apache.bcel.classfile.annotation.RuntimeTypeAnnos;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String user_not_found) {
        super(user_not_found);
    }
}
