package com.abn_amro.usermanagment.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MailServerException extends Throwable {
    public MailServerException(String error_trying_to_send_mail) {
        super(error_trying_to_send_mail);
    }
}
