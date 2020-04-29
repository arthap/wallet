package com.wallet.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalArgumentBalanceException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public IllegalArgumentBalanceException(String message){
        super(message);
    }
}
