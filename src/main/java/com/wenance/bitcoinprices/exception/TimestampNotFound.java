package com.wenance.bitcoinprices.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Timestamp Not Found")
public class TimestampNotFound extends Exception {
    private static final long serialVersionUID = -9079454849611061074L;

    public TimestampNotFound() {
        super();
    }

    public TimestampNotFound(final String message) {
        super(message);
    }
}
