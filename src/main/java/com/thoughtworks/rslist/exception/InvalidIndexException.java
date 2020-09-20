package com.thoughtworks.rslist.exception;

/**
 * Create by 木水 on 2020/9/17.
 */
public class InvalidIndexException extends RuntimeException {
    private String errorMessage;

    public InvalidIndexException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
