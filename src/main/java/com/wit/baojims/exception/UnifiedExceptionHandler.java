package com.wit.baojims.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class UnifiedExceptionHandler {

    @ExceptionHandler(value = BaojiException.class)
    public ModelAndView handlerException(BaojiException e){
        return null;
    }
}
