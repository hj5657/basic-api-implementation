package com.thoughtworks.rslist.compoment;

import com.thoughtworks.rslist.exception.Error;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.exception.InvalidParamException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Create by 木水 on 2020/9/17.
 */
@ControllerAdvice
public class RsEventHandler {
    @ExceptionHandler({InvalidParamException.class, InvalidIndexException.class, MethodArgumentNotValidException.class})
    public ResponseEntity rsExceptionHandler(Exception e) {
        String errorMessage = "";
        if (e instanceof InvalidParamException) {
            errorMessage = e.getMessage();
        }
        if (e instanceof InvalidIndexException) {
            errorMessage = e.getMessage();
        }
        if (e instanceof MethodArgumentNotValidException){
            errorMessage="invalid param";
        }
        Error error = new Error();
        error.setError(errorMessage);
        return ResponseEntity.badRequest().body(error);
    }
}
