package com.thoughtworks.rslist.exception;

/**
 * Create by 木水 on 2020/9/17.
 */
public class InvalidParamException extends RuntimeException {
    private String errorMessage;

    public InvalidParamException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
